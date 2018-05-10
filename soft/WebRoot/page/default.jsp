<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/struts-tags" prefix="s2"%>

<html>
<head><title>首页</title></head>
<body>
    <center>
        
        <table border="0" width="670"cellspacing="0" cellpadding="0" style="margin-top:1">
            <tr><td align="center"><img src="images/pcard2.jpg"></td></tr>
        </table>        
        <!-- 免费专区 -->
        <s2:set name="allsublist" value="#request.allsublist"/>
        <table border="0" width="670" cellspacing="2" cellpadding="0">
            <tr height="35"><td colspan="2" style="text-indent:5" valign="bottom"><font color="#004790"><b>■最新信息</b>『免费专区』</font></td></tr>            
            <s2:if test="#allsublist==null||#allsublist.size()==0">
                <tr height="30"><td align="center" style="border:1 solid">★★★ 在这里显示免费发布的信息！★★★</td></tr>
            </s2:if>
            <s2:else>
                <s2:iterator status="allStatus" value="allsublist">
                    <s2:if test="#allStatus.odd"><tr></s2:if>
                    <td align="center">
                        <table border="1" cellspacing="0" cellpadding="0" width="332" height="160" rules="none" bordercolor="lightgrey" bordercolorlight="lightgrey" bordercolordark="white">
                            <s2:iterator status="oneStatus">
                                <s2:if test="#oneStatus.index==0">
                                  <tr bgcolor="#00B48F" height="30">
                                      <td style="text-indent:10"><b><font color="white">▲<s2:property value="#session.typeMap[infoType]"/></font></b></td>                                      
                                  </tr>
                                </s2:if>
                                <tr bgcolor="#FAFCF5"><td style="text-indent:3">★ <a href="info_SingleShow.action?id=<s2:property value='id'/>"><s2:property value="getSubInfoTitle(20)"/></a></td></tr>
                                <s2:if test="#oneStatus.last">
                                    <tr height="20" bgcolor="#FAFCF5"><td align="right"><a href="info_ListShow.action?infoType=<s2:property value='infoType'/>">更多...</a>&nbsp;&nbsp;</td></tr>                                    
                                </s2:if>
                            </s2:iterator>
                        </table>
                    </td>
                    <s2:if test="#allStatus.even"></tr></s2:if>
                </s2:iterator>
            </s2:else>
        </table>
        <br>
    </center>
</body>
</html>