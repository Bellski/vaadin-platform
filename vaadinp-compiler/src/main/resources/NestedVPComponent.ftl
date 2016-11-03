<#-- @ftlvariable name="" type="ru.vaadinp.compiler.datamodel.GenerateNestedMVPModel" -->
package ${packageName};

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.NestedMVPImpl;
import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.api.NestedMVP;

import javax.inject.Inject;
import javax.inject.Singleton;

<#if presenterComponent.nestedSlotMirror??>
import dagger.Provides;
import ru.vaadinp.annotations.dagger.RevealIn;
import ru.vaadinp.slot.NestedSlot;
</#if>
<#if presenterComponent.nameToken??>
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import ru.vaadinp.annotations.dagger.PlacesMap;
import ru.vaadinp.vp.Place;
import ru.vaadinp.vp.api.PlaceMVP;
</#if>
<#if mvpInfo??>
import ru.vaadinp.vp.MVPInfo;
</#if>

import ${parent.fqn};

<#assign info>
<#if mvpInfo??>

<#assign caption>
${(mvpInfo.caption()?has_content)?string("\"${mvpInfo.caption()}\"", "null")}
</#assign>
<#assign title>
${(mvpInfo.caption()??)?string("\"${mvpInfo.title()}\"", "null")}
</#assign>
<#assign historyToken>
<#if presenterComponent.tokenModel??>
"${presenterComponent.tokenModel.decodedNameTokenConstantName}"
<#else>
null
</#if>
</#assign>
<#assign priority>
${mvpInfo.priority()}
</#assign>
new MVPInfo(${caption}, ${title}, ${historyToken}, ${priority})
</#if>
</#assign>

@Singleton
public class ${name} extends NestedMVPImpl<${presenterComponent.name}> {

    @Module
    public interface Declarations {
        @Binds
        NestedMVP<? extends BaseNestedPresenter<${presenterComponent.apiMirror.viewApiName}>> mvp(${name} mvp);

        @Binds
        ${presenterComponent.apiMirror.viewApiName} view(${presenterComponent.viewMirror.name} view);

        @Binds
        ${presenterComponent.apiMirror.presenterApiName} presenter(${presenterComponent.name} presenter);

        <#if presenterComponent.nestedSlotMirror??>
        @Provides
        @Singleton
        @RevealIn(${presenterComponent.name}.class)
        static NestedSlot nestedSlot() {
            return ${presenterComponent.name}.${presenterComponent.nestedSlotMirror.name};
        }
        </#if>

        <#if presenterComponent.tokenModelList?has_content>
        <#else>
        @IntoMap
        @PlacesMap
        @Binds
        @StringKey(${presenterComponent.tokenModel.encodedNameTokenConstantName})
        PlaceMVP<?> place(${name} mvp);
        </#if>
    }

    @Inject
    public ${name}(Lazy<${presenterComponent.viewMirror.name}> lazyView,
                   Lazy<${presenterComponent.name}> lazyPresenter,
                   RootMVP rootMVP,
                   ${parent.name} parent) {
            <#assign placeInit>
            <#if presenterComponent.tokenModelList?has_content>
            ${(presenterComponent.nameToken??)?string("new Place(${presenterComponent.tokenModelList?join(",")})", "null")}
            <#else>
            ${(presenterComponent.nameToken??)?string("new Place(${presenterComponent.tokenModel.tokenNameConstantName})", "null")}
            </#if>
            </#assign>
            super(
                null,
                lazyView,
                lazyPresenter,
                ${(mvpInfo??)?string("${info}", "null")},
                rootMVP,
                parent,
                ${placeInit}
            );
    }
}