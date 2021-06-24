package software_metrics;

import classes.NumberOfChanges;
import classes.Sourcefile;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import core.VersionNameManager;
import ucl.cdt.cybersecurity.App;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CodeChurnLookUp {

    public int lookupCodeChurnValue(Sourcefile sourcefile, ClassOrInterfaceDeclaration classDeclaration, MethodDeclaration methodDeclaration) throws IOException {

        int codeChurnValue = 0;
        HashMap<String, NumberOfChanges> numberOfChangesMap = App.getNumberOfChangesMap();
        String filepath = sourcefile.getFilepath();
        String versionName = new VersionNameManager().getVersionName(filepath);
        String filepathSuffix = filepath.split(versionName)[1];
        String className = classDeclaration.getNameAsString();
        String methodSignature = methodDeclaration.getSignature().toString();
        String methodContent = methodDeclaration.toString();
        String key = versionName + "=+=" + filepathSuffix + "=+=" +  className + "=+=" + methodSignature + "=+=" + methodContent;
        ArrayList<String> versionNamesList = new ArrayList<>(new VersionNameManager().getVersionNames());  // LinkedHashSet to ArrayList conversion.

        // If a key returns null, that means that the associated method did not satisfy the conditions in 'CodeChurnDataBuilder.java' that would have allowed it to be included in the 'churnedKeys' map.
        if (numberOfChangesMap.containsKey(key)) {

            codeChurnValue = numberOfChangesMap.get(key).getCodeChurnValue();

            // Remove the map entry after writing it to CSV to help the memory footprint.
            numberOfChangesMap.remove(key);
        }
        return codeChurnValue;
    }
}
