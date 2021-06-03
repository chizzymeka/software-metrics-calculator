package software_metrics;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import classes.Sourcefile;
import core.VersionNameManager;
import ucl.cdt.cybersecurity.App;

import java.io.IOException;
import java.util.HashMap;

public class CodeChurnLookUp {

    public long lookupCodeChurnValue(Sourcefile sourcefile, ClassOrInterfaceDeclaration classDeclaration, MethodDeclaration methodDeclaration) throws IOException {

        long numberOfChanges = 0;
        HashMap<String, Long> numberOfChangesMap = new App().getNumberOfChangesMap();
        String filepath = sourcefile.getFilepath();
        String versionName = new VersionNameManager().getVersionName(filepath);
        String filepathSuffix = filepath.split(versionName)[1];
        String className = classDeclaration.getNameAsString();
        String methodSignature = methodDeclaration.getSignature().toString();
        String key = versionName + "=+=" + filepathSuffix + "=+=" +  className + "=+=" + methodSignature;

        // If a key returns null, that means that the associated method did not satisfy the conditions in 'CodeChurnDataBuilder.java' that would have allowed it to be included in the 'numberOfChangesMap' map.
        if (numberOfChangesMap.containsKey(key)) {

            numberOfChanges = numberOfChangesMap.get(key);
            // Remove the map entry after writing it to CSV to help the memory footprint.
            numberOfChangesMap.remove(key);

        }

        return numberOfChanges;
    }
}
