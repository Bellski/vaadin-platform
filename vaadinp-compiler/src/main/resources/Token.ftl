<#-- @ftlvariable name="" type="ru.vaadinp.compiler.datamodel.AnnotatedNestedPresenter" -->
package ${packageName};

import ru.vaadinp.place.NameToken;

public class ${apiMirror.name}Token {
<#list tokenSetModel.tokenModelList as tokenModel>
    public static final String ${tokenModel.encodedNameTokenConstantName} = "${tokenModel.decodedNameToken}";
<#if tokenModel.parameterNames??>
<#assign joinedParamNames>
${tokenModel.parameterNames?join(",")}
</#assign>
<#assign joinedParamIndexes>
${tokenModel.parameterIndexes?join(",")}
</#assign>
    static final NameToken ${tokenModel.encodedNameTokenConstantName}_TOKEN = new NameToken(${tokenModel.decodedNameToken}, ${tokenModel.encodedNameTokenConstantName}, new String[] {${joinedParamNames}, new int[] {${joinedParamIndexes});
<#else>
    static final NameToken ${tokenModel.encodedNameTokenConstantName}_TOKEN = new NameToken(${tokenModel.encodedNameTokenConstantName});
</#if>
</#list>
}