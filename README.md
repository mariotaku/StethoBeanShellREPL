# Stetho BeanShell REPL extension

---

## Why?

BeanShell has more friendly syntax for Java developers, and it doesn't add too much 
size and method count compared to Rhino.

## How?

````java
import android.app.Application;
import com.facebook.stetho.Stetho;
import org.mariotaku.stethoext.bsh.BshRuntimeReplFactoryBuilder;
 
/*
 * Use snippet below 
 */
public class YourApplication extends Application {
    
    public void onCreate() {
        super.onCreate();
        
        Stetho.initialize(Stetho.newInitializerBuilder(this)
            .enableDumpapp(() -> Stetho.defaultDumperPluginsProvider(this))
            .enableWebKitInspector(() -> new Stetho.DefaultInspectorModulesBuilder(this)
                .runtimeRepl(new BshRuntimeReplFactoryBuilder(this).build())
                .finish())
            .build());
    }
    
}
````