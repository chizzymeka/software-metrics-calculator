package software_metrics;

import classes.Sourcefile;
import com.github.javaparser.ast.body.MethodDeclaration;
import core.VersionNameManager;
import ucl.cdt.cybersecurity.App;

import java.io.IOException;
import java.util.HashMap;

public class CodeChurnLookUp {

    public int lookupCodeChurnValue(Sourcefile sourcefile, String className, MethodDeclaration methodDeclaration) throws IOException {

        int codeChurnValue = 0;
        String filepath = sourcefile.getFilepath();
        String versionName = new VersionNameManager().getVersionName(filepath);
        String filepathSuffix = filepath.split(versionName)[1];
        String methodSignature = methodDeclaration.getSignature().toString();

         /*
            'keyMap' maps the string key to the methodId. We then formulate the string keys (as per below) from the sourcefile objects and use it to obtain the methodId which we then use to obtain the code churn value.
            This approach allows the use of an int key in 'CodeChurnDataBuilder'.java to avoid the 'GC overhead limit exceeded' OutOfMemory exception that occurs when we use the string key directly.
         */
        HashMap<String, Integer> keyMap = App.getKeyMap();
        String key = versionName + filepathSuffix + className + methodSignature.intern();

        if (keyMap.containsKey(key)) {

            int methodId = keyMap.get(key);
            HashMap<Integer, Integer> numberOfChangesMap = App.getNumberOfChangesMap();

            // If a key returns null, that means that the associated method did not satisfy the conditions in 'CodeChurnDataBuilder.java' that would have allowed it to be included in the 'churnedKeys' map.
            if (numberOfChangesMap.containsKey(methodId)) {
                codeChurnValue = numberOfChangesMap.get(methodId);
            }
        }

        return codeChurnValue;
    }
}
