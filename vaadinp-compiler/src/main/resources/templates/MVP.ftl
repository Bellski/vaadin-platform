<#-- @ftlvariable name="" type="ru.vaadinp.compiler.datamodel.MVPModel" -->
package ${packageName};

<#list imports as import>
import ${import};
</#list>


@Singleton
public class ${name} extends MVPImpl<${presenterImplName}> {

    @Module
    public interface Declarations {
        @Binds
        MVP<? extends PresenterComponent<${viewApiName}>> mvp(${name} mvp);

        @Binds
        ${viewApiName} view(${viewImplName} view);

        @Binds
        ${presenterApiName} presenter(${presenterImplName} presenter);
    }

    @Inject
    public ${name}(Lazy<${viewImplName}> lazyView, Lazy<${presenterImplName}> lazyPresenter, RootMVP rootMVP) {
        super(lazyView, lazyPresenter, rootMVP);
    }
}