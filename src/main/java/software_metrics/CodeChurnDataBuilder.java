// Map Serialization and Deserialization with Jackson: https://www.baeldung.com/jackson-map
// How to create a JSON array using Jackson: https://attacomsian.com/blog/jackson-create-json-array
// Get single field from JSON using Jackson: https://stackoverflow.com/questions/26190851/get-single-field-from-json-using-jackson

package software_metrics;

import classes.CodeChurn;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import classes.Sourcefile;
import core.VersionNameManager;
import ucl.cdt.cybersecurity.App;
import utilities.SourcefileNameExtractor;
import utilities.TaskProgressReporter;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class CodeChurnDataBuilder {

    public void buildCodeChurnData(Sourcefile sourcefile) throws IOException {

        new TaskProgressReporter().count();
        HashMap<String, HashSet<CodeChurn>> codeChurnDataMap = new App().getCodeChurnDataMap();
        HashSet<CodeChurn> codeChurnObjects = new App().getCodeChurnObjects();
        List<ClassOrInterfaceDeclaration> classDeclarations = sourcefile.getClassDeclarations();
        String filepath = sourcefile.getFilepath();
        String versionName = new VersionNameManager().getVersionName(filepath);
        String filepathSuffix = filepath.split(versionName)[1];
        String sourceFileName = new SourcefileNameExtractor().extractSourcefileName(filepath);

        for (ClassOrInterfaceDeclaration classDeclaration : classDeclarations) {

            CodeChurn codeChurn = new CodeChurn();
            codeChurn.setFilepath(filepath);
            codeChurn.setFilepathSuffix(filepathSuffix);
            codeChurn.setVersionName(versionName);
            codeChurn.setSourcefileName(sourceFileName);

            String className = classDeclaration.getNameAsString();
            codeChurn.setClassName(className);

            List<MethodDeclaration> methodDeclarations = classDeclaration.getMethods();
            HashMap<String, String> methods = new HashMap<>();

            for (MethodDeclaration methodDeclaration : methodDeclarations) {
                String methodSignature = methodDeclaration.getSignature().toString();
                String methodContent = methodDeclaration.toString();
                methods.put(methodSignature, methodContent);
            }

            codeChurn.setMethods(methods);
            codeChurnObjects.add(codeChurn);

        }

        new App().setCodeChurnObjects(codeChurnObjects);
        codeChurnObjects = new App().getCodeChurnObjects();
        codeChurnDataMap.put(versionName, codeChurnObjects);
        new App().setCodeChurnDataMap(codeChurnDataMap);
        codeChurnDataMap = new App().getCodeChurnDataMap();

        long numberOfProcessedFiles = new App().getNumberOfprocessedFiles();
        long totalNumberOfJavaSourceFilesInDataset = new App().getTotalNumberOfJavaSourcefilesInDataset();
        LinkedHashSet<String> versionNames = new VersionNameManager().getVersionNames();

        if (numberOfProcessedFiles == totalNumberOfJavaSourceFilesInDataset) {

            int currentCodeChurnObject = 0;
            int totalSize = 0;

            for (Entry<String, HashSet<CodeChurn>> entry : codeChurnDataMap.entrySet()) {
                totalSize += entry.getValue().size();
            }

            // The code below traverses the entire dataset and compares every method to their counterparts in other version to detect changes.
            for (Entry<String, HashSet<CodeChurn>> entry_outer : codeChurnDataMap.entrySet()) {

                String versionName_outer = entry_outer.getKey();
                HashSet<CodeChurn> codeChurnObjects_outer = entry_outer.getValue();

                for (CodeChurn codeChurnObject_outer : codeChurnObjects_outer) {

                    for (Entry<String, HashSet<CodeChurn>> entry_inner : codeChurnDataMap.entrySet()) {

                        String versionName_inner = entry_inner.getKey();
                        HashSet<CodeChurn> codeChurnObjects_inner = entry_inner.getValue();

                        // Check that versions are NOT the same.
                        if (!versionName_outer.equals(versionName_inner)) {

                            currentCodeChurnObject++;
                            // We multiply 'totalSize' by two to account for the report generation which will follow after this loop has completed.
                            System.out.println("Currently working on: " + currentCodeChurnObject + " out of " + totalSize * 2 + ".");

                            for (CodeChurn codeChurnObject_inner : codeChurnObjects_inner) {

                                // Check that filepath suffixes are the same. This implies that the source files are the same as well but across different versions.
                                if (codeChurnObject_outer.getFilepathSuffix().equals(codeChurnObject_inner.getFilepathSuffix())) {

                                    String className_outer = codeChurnObject_outer.getClassName();
                                    String className_inner = codeChurnObject_inner.getClassName();
                                    HashMap<String, String> methods_outer = codeChurnObject_outer.getMethods();
                                    HashMap<String, String> methods_inner = codeChurnObject_inner.getMethods();
                                    String filePathSuffix_outer = codeChurnObject_outer.getFilepathSuffix();
                                    String filePathSuffix_inner = codeChurnObject_inner.getFilepathSuffix();

                                    for (Entry<String, String> method_outer : methods_outer.entrySet()) {

                                        String methodSignature_outer = method_outer.getKey();
                                        String methodContent_outer = method_outer.getValue();

                                        for (Entry<String, String> method_inner : methods_inner.entrySet()) {

                                            String methodSignature_inner = method_inner.getKey();
                                            String methodContent_inner = method_inner.getValue();

                                            // Check that the class names are the same.
                                            if (className_outer.equals(className_inner)) {

                                                // Check that the method signatures are the same.
                                                if (methodSignature_outer.equals(methodSignature_inner)) {

                                                    // Check that the method contents are NOT the same.
                                                    if (!methodContent_outer.equals(methodContent_inner)) {

                                                        // System.out.println("Change detected!");

                                                        //------------------------------------------------------------------------------------------------------------------------
                                                        // The code below ensures that code changes across versions are properly tracked and updated.
                                                        // This code churn functionality heavily relies on The Alphanum Algorithm -  see 'VersionNameManager.java'.
                                                        // The algorithm sorts the dataset versions from earliest to most recent based on the version numbers in the version folders. For example, from ‘apache-tomcat-7.0.0-src’ to ‘apache-tomcat-7.0.108-src’.
                                                        // We then store these versions in a LinkedHashSet to preserve insertion order.
                                                        // Suppose a change is detected when assessing code churn. In that case, we assign the index number (from the set) of the version as the number of code churn value.
                                                        // So, as long as the folders containing the different versions in the dataset are appropriately named, the code churn will work as expected.
                                                        //------------------------------------------------------------------------------------------------------------------------

                                                        ArrayList<String> versionNamesList = new ArrayList<>(versionNames);
                                                        int versionName_outer_index = versionNamesList.indexOf(versionName_outer);
                                                        String key = versionName_outer + "=+=" + filePathSuffix_outer + "=+=" +  className_outer + "=+=" + methodSignature_outer;
                                                        HashMap<String, Long> numberOfChangesMap = new App().getNumberOfChangesMap();
                                                        long numberOfChanges = 0L;

                                                        if (numberOfChangesMap.containsKey(key)) {
                                                            numberOfChanges = versionName_outer_index;
                                                        }

                                                        numberOfChangesMap.put(key, numberOfChanges);

                                                        //------------------------------------------------------------------------------------------------------------------------
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
            // System.exit(0);
        }
    }
}
