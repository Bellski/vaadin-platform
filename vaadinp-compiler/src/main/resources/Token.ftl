<#-- @ftlvariable name="" type="ru.vaadinp.compiler.datamodel.TokenModel" -->
package ${name};

import ru.vaadinp.place.NameToken;

public class ${name} {
    public static final String ${encodedNameTokenConstantName} = "${decodedNameToken}";
<#if parameterNames??>
<#assign joinedParamNames>
${parameterNames?join(",")}
</#assign>
<#assign joinedParamIndexes>
${parameterIndexes?join(",")}
</#assign>
    static final NameToken ${encodedNameTokenConstantName}_TOKEN = new NameToken(${decodedNameToken}, ${encodedNameTokenConstantName}, new String[] {${joinedParamNames}, new int[] {${joinedParamIndexes});
<#else>
    static final NameToken ${encodedNameTokenConstantName}_TOKEN = new NameToken(${encodedNameTokenConstantName});
</#if>
}