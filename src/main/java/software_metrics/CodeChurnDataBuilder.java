// Map Serialization and Deserialization with Jackson: https://www.baeldung.com/jackson-map
// How to create a JSON array using Jackson: https://attacomsian.com/blog/jackson-create-json-array
// Get single field from JSON using Jackson: https://stackoverflow.com/questions/26190851/get-single-field-from-json-using-jackson

package software_metrics;

import classes.CodeChurn;
import classes.NumberOfChanges;
import classes.Sourcefile;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import core.VersionNameManager;
import ucl.cdt.cybersecurity.App;
import utilities.SourcefileNameExtractor;
import utilities.TaskProgressReporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

public class CodeChurnDataBuilder {

    public void buildCodeChurnData(Sourcefile sourcefile) throws IOException {

        new TaskProgressReporter().showProgress();
        HashMap<String, HashSet<CodeChurn>> codeChurnDataMap = new App().getCodeChurnDataMap();
        HashSet<CodeChurn> codeChurnObjects = new App().getCodeChurnObjects();
        List<ClassOrInterfaceDeclaration> classOrInterfaceDeclarations = sourcefile.getClassDeclarations();
        String filepath = sourcefile.getFilepath();
        String versionName = new VersionNameManager().getVersionName(filepath);
        String filepathSuffix = filepath.split(versionName)[1];
        String sourceFileName = new SourcefileNameExtractor().extractSourcefileName(filepath);
        ArrayList<String> versionNamesList = new ArrayList<>(new VersionNameManager().getVersionNames());  // LinkedHashSet to ArrayList conversion.

        for (ClassOrInterfaceDeclaration classOrInterfaceDeclaration : classOrInterfaceDeclarations) {

            CodeChurn codeChurn = new CodeChurn();
            codeChurn.setFilepath(filepath);
            codeChurn.setFilepathSuffix(filepathSuffix);
            codeChurn.setVersionName(versionName);
            codeChurn.setSourcefileName(sourceFileName);

            String className = classOrInterfaceDeclaration.getNameAsString();
            codeChurn.setClassName(className);

            List<MethodDeclaration> methodDeclarations = classOrInterfaceDeclaration.getMethods();
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

        long numberOfProcessedFiles = new App().getNumberOfProcessedFiles();
        long totalNumberOfJavaSourceFilesInDataset = new App().getTotalNumberOfJavaSourceFilesInDataset();

        if (numberOfProcessedFiles == totalNumberOfJavaSourceFilesInDataset) {

            HashMap<String, NumberOfChanges> numberOfChangesMap = App.getNumberOfChangesMap();

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

                                                    String key = versionName_outer + "=+=" + filePathSuffix_outer + "=+=" +  className_outer + "=+=" + methodSignature_outer + "=+=" + methodContent_outer;
                                                    NumberOfChanges numberOfChangesObject = new NumberOfChanges();
                                                    int numberOfChanges;

                                                    // Check that the method contents are NOT the same.
                                                    if (!methodContent_outer.equals(methodContent_inner)) {

                                                        // System.out.println("Change detected!");

                                                        if (!numberOfChangesMap.containsKey(key)) {
                                                            numberOfChangesObject = new NumberOfChanges();
                                                        } else {
                                                            numberOfChangesObject = numberOfChangesMap.get(key);
                                                        }
                                                        numberOfChanges = versionNamesList.indexOf(versionName_outer);
                                                        numberOfChangesObject.setCodeChurnValue(numberOfChanges);
                                                    } else {
                                                        // When no code churn is detected, this 'else' section doubles back to reuse the positive last code churn value.

                                                        int index = versionNamesList.indexOf(versionName_outer);
                                                        numberOfChanges = numberOfChangesObject.getCodeChurnValue();

                                                        // if ((index > 0) && (numberOfChanges == 0)) {
                                                        //    numberOfChanges = index - 1;
                                                        //    numberOfChangesObject.setCodeChurnValue(numberOfChanges);
                                                        // }

                                                        // Check that the key is not associated with the oldest version but has zero changes
                                                        if ((index > 0) && (numberOfChanges == 0)) {
                                                            while (index > 0) {

                                                                --index;

                                                                if ((numberOfChangesMap.containsKey(key)) && (index != 0) && (numberOfChangesMap.get(key).getCodeChurnValue() <= index)) {
                                                                    numberOfChanges = index;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                        numberOfChangesObject.setCodeChurnValue(numberOfChanges);
                                                    }
                                                    numberOfChangesMap.put(key, numberOfChangesObject);
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

            // Files.write(Paths.get("churnedKeys.txt"), () -> churnedKeys.entrySet().stream().<CharSequence>map(e -> e.getKey() + ":" + e.getValue()).iterator());
            // Files.write(Paths.get("unChurnedKeys.txt"), () -> unChurnedKeys.entrySet().stream().<CharSequence>map(e -> e.getKey() + ":" + e.getValue()).iterator());

            // System.exit(0);
            // Reset the number of processed files counter after building code churn data so that it will start afresh for CSV writing.
            new App().setNumberOfProcessedFiles(0L);
        }
    }
}
