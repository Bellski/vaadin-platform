<#-- @ftlvariable name="" type="ru.vaadinp.compiler2.datamodel.TokenSetModel" -->
package ${packageName};

import ru.vaadinp.place.NameToken;

public class ${name} {
<#list tokenModels as tokenModel>
    public static final String ${tokenModel.encodedNameTokenFieldName} = "${tokenModel.encodedNameToken}";
    public static final String ${tokenModel.decodedNameTokenFieldName} = "${tokenModel.decodedNameToken}";
    <#if tokenModel.parameterNames?has_content>
    static final NameToken ${tokenModel.simplifiedNameToken?upper_case}_TOKEN = new NameToken(${tokenModel.encodedNameTokenFieldName}, ${tokenModel.decodedNameTokenFieldName}, new String[] {${tokenModel.joinedParamNames()}}, new int[] {${tokenModel.joinedParamIndexes()}});
    <#else>
    static final NameToken ${tokenModel.simplifiedNameToken?upper_case}_TOKEN = new NameToken(${tokenModel.encodedNameTokenFieldName}, ${tokenModel.decodedNameTokenFieldName});

    </#if>
</#list>
}