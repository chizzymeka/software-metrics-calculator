// Map Serialization and Deserialization with Jackson: https://www.baeldung.com/jackson-map
// How to create a JSON array using Jackson: https://attacomsian.com/blog/jackson-create-json-array
// Get single field from JSON using Jackson: https://stackoverflow.com/questions/26190851/get-single-field-from-json-using-jackson
// Combining the Jackson Streaming API with ObjectMapper for parsing JSON (see - 'Generating JSON with JsonGenerator' section): https://cassiomolin.com/2019/08/19/combining-jackson-streaming-api-with-objectmapper-for-parsing-json/

package software_metrics;

import classes.CodeChurn;
import classes.Method;
import classes.MethodDeclarationAndAttributes;
import classes.Sourcefile;
import core.VersionNameManager;
import ucl.cdt.cybersecurity.App;
import utilities.CurrentTime;
import utilities.SourcefileNameExtractor;
import utilities.TaskProgressReporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class CodeChurnDataBuilder {

    public void buildCodeChurnData(Sourcefile sourcefile, int sourceFileId) throws IOException {

        new TaskProgressReporter().showProgress();

        HashMap<String, HashSet<CodeChurn>> codeChurnDataMap = new App().getCodeChurnDataMap();
        HashMap<String, HashSet<MethodDeclarationAndAttributes>> classNameAndMethodDeclarationAndAttributes = sourcefile.getClassNameAndMethodDeclarationAndAttributes();

        String filepath = sourcefile.getFilepath();
        String versionName = new VersionNameManager().getVersionName(filepath);
        String filepathSuffix = filepath.split(versionName)[1];
        String sourceFileName = new SourcefileNameExtractor().extractSourcefileName(filepath);

        ArrayList<String> versionNamesList = new App().getVersionNamesList(); // LinkedHashSet to ArrayList conversion. Note that this list of version names is sorted using the 'AlphanumComparator' algorithm.

        for (Entry<String, HashSet<MethodDeclarationAndAttributes>> entry : classNameAndMethodDeclarationAndAttributes.entrySet()) {

            CodeChurn codeChurn = new CodeChurn();
            codeChurn.setCodeChurnObjectId(sourceFileId);
            codeChurn.setFilepath(filepath);
            codeChurn.setFilepathSuffix(filepathSuffix);
            codeChurn.setVersionName(versionName);
            codeChurn.setSourcefileName(sourceFileName);

            String className = entry.getKey();
            codeChurn.setClassName(className);

            HashSet<MethodDeclarationAndAttributes> methodDeclarationAndAttributes = entry.getValue();
            HashSet<Method> methods = new HashSet<>();

            for (MethodDeclarationAndAttributes methodDeclarationAndAttribute : methodDeclarationAndAttributes) {

                Method method = new Method();
                method.setMethodId(methodDeclarationAndAttribute.getMethodId());

                String methodSignature = methodDeclarationAndAttribute.getMethodDeclaration().getSignature().toString();
                method.setMethodSignature(methodSignature);

                String methodContent = methodDeclarationAndAttribute.getMethodDeclaration().toString();
                methodContent = methodContent.replaceAll("\\s",""); // Remove all whitespaces.
                method.setMethodContent(methodContent);
                methods.add(method);
            }

            codeChurn.setMethods(methods);

            if (!codeChurnDataMap.containsKey(versionName)) {
                HashSet<CodeChurn> codeChurnObjects = new HashSet<>();
                codeChurnDataMap.put(versionName, codeChurnObjects);
            }
            codeChurnDataMap.get(versionName).add(codeChurn);
        }

        long numberOfProcessedFiles = new App().getNumberOfProcessedFiles();
        long totalNumberOfJavaSourceFilesInDataset = new App().getTotalNumberOfJavaSourceFilesInDataset();

        if (numberOfProcessedFiles == totalNumberOfJavaSourceFilesInDataset) {

            // The code below sorts the 'codeChurnDataMap' map by version names (keys), from oldest to newest.
            LinkedHashMap<String, HashSet<CodeChurn>> sortedCodeChurnDataMap = new LinkedHashMap<>();
            for (String version : versionNamesList) {
                if (codeChurnDataMap.containsKey(version)) {
                    sortedCodeChurnDataMap.put(version, codeChurnDataMap.get(version));
                }
            }

            HashMap<Integer, Integer> numberOfChangesMap = App.getNumberOfChangesMap();

            // The code below traverses the entire dataset and compares every method to their counterparts in other version to detect changes.
            for (Entry<String, HashSet<CodeChurn>> entry_outer : sortedCodeChurnDataMap.entrySet()) {

                HashSet<CodeChurn> codeChurnObjects_outer = entry_outer.getValue();

                for (CodeChurn codeChurnObject_outer : codeChurnObjects_outer) {

                    for (Entry<String, HashSet<CodeChurn>> entry_inner : sortedCodeChurnDataMap.entrySet()) {

                        HashSet<CodeChurn> codeChurnObjects_inner = entry_inner.getValue();

                        for (CodeChurn codeChurnObject_inner : codeChurnObjects_inner) {

                            String versionName_outer = entry_outer.getKey();
                            String versionName_inner = entry_inner.getKey();

                            // Check that versions are NOT the same.
                            if (versionName_outer.hashCode() != versionName_inner.hashCode()) {

                                String filePathSuffix_outer = codeChurnObject_outer.getFilepathSuffix();
                                String filePathSuffix_inner = codeChurnObject_inner.getFilepathSuffix();

                                // Check that filepath suffixes are the same. This implies that the source files are the same as well but across different versions.
                                if (filePathSuffix_outer.hashCode() == filePathSuffix_inner.hashCode()) {

                                    HashSet<Method> methods_outer = codeChurnObject_outer.getMethods();
                                    HashSet<Method> methods_inner = codeChurnObject_inner.getMethods();

                                    for (Method method_outer : methods_outer) {

                                        for (Method method_inner : methods_inner) {

                                            String className_outer = codeChurnObject_outer.getClassName();
                                            String className_inner = codeChurnObject_inner.getClassName();

                                            // Check that the class names are the same.
                                            if (className_outer.hashCode() == className_inner.hashCode()) {

                                                String methodSignature_outer = method_outer.getMethodSignature();
                                                String methodSignature_inner = method_inner.getMethodSignature();

                                                // Check that the method signatures are the same.
                                                if (methodSignature_outer.hashCode() == methodSignature_inner.hashCode()) {

                                                    int methodId = method_outer.getMethodId();
                                                    int numberOfChanges = 0;

                                                    String methodContent_outer = method_outer.getMethodContent();
                                                    String methodContent_inner = method_inner.getMethodContent();

                                                    // Check that the method contents are NOT the same.
                                                    if (methodContent_outer.hashCode() != methodContent_inner.hashCode()) {

                                                        // System.out.println("Change detected!");

                                                        if (numberOfChangesMap.containsKey(methodId)) {
                                                            numberOfChanges = numberOfChangesMap.get(methodId);
                                                        }
                                                        numberOfChanges++;
                                                    }

                                                    // The if-statement check (before insertion) below helps sort the CSV by ensuring that the lower code churn values start appearing at the beginning of the CSV file.
                                                    if (numberOfChanges <= versionNamesList.indexOf(versionName_outer)) {
                                                        numberOfChangesMap.put(methodId, numberOfChanges);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("Code churn data build completed...[" + new CurrentTime().getCurrentTimeStamp() + "]");

            // System.exit(0);
            // Reset the 'number of processed files' counter after building code churn data so that it will start afresh for CSV writing.
            new App().setNumberOfProcessedFiles(0L);
        }
    }
}
