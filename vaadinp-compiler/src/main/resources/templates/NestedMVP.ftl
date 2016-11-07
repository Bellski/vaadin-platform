<#-- @ftlvariable name="" type="ru.vaadinp.compiler2.datamodel.NestedMVPModel" -->
package ${packageName};

<#list imports as import>
import ${import};
</#list>

@Singleton
public class ${name} extends NestedMVPImpl<${presenterImplName}> {

    @Module
    public interface Declarations {
        @Binds
        NestedMVP<? extends BaseNestedPresenter<${viewApiName}>> mvp(${name} mvp);

        @Binds
        ${viewApiName} view(${viewImplName} view);

        @Binds
        ${presenterApiName} presenter(${presenterImplName} presenter);

        <#list tokenModels as tokenModel>
        @IntoMap
        @PlacesMap
        @Binds
        @StringKey(${tokenModel.encodedNameTokenConstantName})
        PlaceMVP<?> ${tokenModel.simplifiedNameToken}_place(${name} mvp);
        </#list>
        <#if nestedSlotConstantName??>
        @Provides
        @Singleton
        @RevealIn(${presenterImplName}.class)
        static NestedSlot nestedSlot() {
            return ${nestedSlotConstantName};
        }
        </#if>
    }

    @Inject
    public ${name}(Lazy<${viewImplName}> lazyView,
                   Lazy<${presenterImplName}> lazyPresenter,
                   RootMVP rootMVP,
                   ${parentName} parent) {
        super (
            null,
            lazyView,
            lazyPresenter,
            ${(hasMVPInfo())?string("new MVPInfo(\"${caption}\", \"${title}\", \"${historyToken}\", ${priority})", "null")},
            rootMVP,
            parent,
            ${(hasTokenSetModel())?string("new Place(${tokenNameConstantName?join(',')})", 'null')}
        );
    }
}