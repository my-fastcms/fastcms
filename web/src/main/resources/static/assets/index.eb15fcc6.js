import L from"./index.c2487e2c.js";import{_ as A,u as C,j as g,c as y}from"./index.fabd0e58.js";import{J as B,a6 as k,L as E,l as _,a7 as z,m as $,z as e,A as s,b as r,D as I,p as t,w as a,x as u}from"./vendor.09ddcd37.js";const U={name:"auth",props:{value:{type:String,default:()=>""}},setup(o){const n=C();return{getUserAuthBtnList:B(()=>n.state.userInfos.userInfos.authBtnList.some(i=>i===o.value))}}};function j(o,n,d,i,f,h){return i.getUserAuthBtnList?k(o.$slots,"default",{key:0}):E("",!0)}var N=A(U,[["render",j]]);const S={name:"auths",props:{value:{type:Array,default:()=>[]}},setup(o){const n=C();return{getUserAuthBtnList:B(()=>{let i=!1;return n.state.userInfos.userInfos.authBtnList.map(f=>{o.value.map(h=>{f===h&&(i=!0)})}),i})}}};function V(o,n,d,i,f,h){return i.getUserAuthBtnList?k(o.$slots,"default",{key:0}):E("",!0)}var P=A(S,[["render",V]]);const J={name:"authAll",props:{value:{type:Array,default:()=>[]}},setup(o){const n=C();return{getUserAuthBtnList:B(()=>g(o.value,n.state.userInfos.userInfos.authBtnList))}}};function M(o,n,d,i,f,h){return i.getUserAuthBtnList?k(o.$slots,"default",{key:0}):E("",!0)}var T=A(J,[["render",M]]);function q(o){return y.state.userInfos.userInfos.authBtnList.some(n=>n===o)}function G(o){let n=!1;return y.state.userInfos.userInfos.authBtnList.map(d=>{o.map(i=>{d===i&&(n=!0)})}),n}function H(o){return g(o,y.state.userInfos.userInfos.authBtnList)}const K={name:"limitsFrontEndBtn",components:{LimitsFrontEndPage:L,Auth:N,Auths:P,AuthAll:T},setup(){return{onAuthClick:()=>{q("btn.add")?r.success("\u606D\u559C\uFF0C\u60A8\u6709\u6743\u9650\uFF01"):r.error("\u62B1\u6B49\uFF0C\u60A8\u6CA1\u6709\u6743\u9650\uFF01")},onAuthsClick:()=>{G(["btn.add","btn.edit","btn.del","btn.link"])?r.success("\u606D\u559C\uFF0C\u60A8\u6709\u6743\u9650\uFF01"):r.error("\u62B1\u6B49\uFF0C\u60A8\u6CA1\u6709\u6743\u9650\uFF01")},onAuthAllClick:()=>{H(["btn.add","btn.edit","btn.del","btn.link"])?r.success("\u606D\u559C\uFF0C\u60A8\u6709\u6743\u9650\uFF01"):r.error("\u62B1\u6B49\uFF0C\u60A8\u6CA1\u6709\u6743\u9650\uFF01")}}}},O=u('\u5355\u4E2A\u6743\u9650\u9A8C\u8BC1\uFF08:value="xxx"\uFF09\uFF1A'),Q={class:"flex-warp"},R={class:"flex-warp-item"},W={class:"flex-warp-item-box"},X=u("\u65B0\u589E "),Y={class:"flex-warp-item"},Z={class:"flex-warp-item-box"},tt=u("\u7F16\u8F91"),et={class:"flex-warp-item"},st={class:"flex-warp-item-box"},ut=u("\u5220\u9664 "),lt={class:"flex-warp-item"},ot={class:"flex-warp-item-box"},it=u("\u8DF3\u8F6C "),nt=u('\u591A\u4E2A\u6743\u9650\u9A8C\u8BC1\uFF0C\u6EE1\u8DB3\u4E00\u4E2A\u5219\u663E\u793A\uFF08:value="[xxx,xxx]"\uFF09\uFF1A'),at={class:"flex-warp"},dt={class:"flex-warp-item"},ct={class:"flex-warp-item-box"},_t=u("\u65B0\u589E "),rt={class:"flex-warp-item"},ft={class:"flex-warp-item-box"},ht=u("\u7F16\u8F91"),mt={class:"flex-warp-item"},xt={class:"flex-warp-item-box"},pt=u("\u5220\u9664 "),Ft={class:"flex-warp-item"},bt={class:"flex-warp-item-box"},vt=u("\u8DF3\u8F6C "),At=u('\u591A\u4E2A\u6743\u9650\u9A8C\u8BC1\uFF0C\u5168\u90E8\u6EE1\u8DB3\u5219\u663E\u793A\uFF08:value="[xxx,xxx]"\uFF09\uFF1A'),wt={class:"flex-warp"},Ct={class:"flex-warp-item"},yt={class:"flex-warp-item-box"},Bt=u("\u65B0\u589E "),kt={class:"flex-warp-item"},Et={class:"flex-warp-item-box"},zt=u("\u7F16\u8F91"),gt={class:"flex-warp-item"},Dt={class:"flex-warp-item-box"},Lt=u("\u5220\u9664 "),$t={class:"flex-warp-item"},It={class:"flex-warp-item-box"},Ut=u("\u8DF3\u8F6C "),jt=u('\u5355\u4E2A\u6743\u9650\u9A8C\u8BC1\uFF08v-auth="xxx"\uFF09\uFF1A'),Nt={class:"flex-warp"},St={class:"flex-warp-item"},Vt={class:"flex-warp-item-box"},Pt=u("\u65B0\u589E "),Jt={class:"flex-warp-item"},Mt={class:"flex-warp-item-box"},Tt=u("\u7F16\u8F91"),qt={class:"flex-warp-item"},Gt={class:"flex-warp-item-box"},Ht=u("\u5220\u9664 "),Kt={class:"flex-warp-item"},Ot={class:"flex-warp-item-box"},Qt=u("\u8DF3\u8F6C "),Rt=u('\u591A\u4E2A\u6743\u9650\u9A8C\u8BC1\uFF0C\u6EE1\u8DB3\u4E00\u4E2A\u5219\u663E\u793A\uFF08v-auths="[xxx,xxx]"\uFF09\uFF1A'),Wt={class:"flex-warp"},Xt={class:"flex-warp-item"},Yt={class:"flex-warp-item-box"},Zt=u("\u65B0\u589E "),te={class:"flex-warp-item"},ee={class:"flex-warp-item-box"},se=u("\u7F16\u8F91"),ue={class:"flex-warp-item"},le={class:"flex-warp-item-box"},oe=u("\u5220\u9664 "),ie={class:"flex-warp-item"},ne={class:"flex-warp-item-box"},ae=u("\u8DF3\u8F6C "),de=u('\u591A\u4E2A\u6743\u9650\u9A8C\u8BC1\uFF0C\u5168\u90E8\u6EE1\u8DB3\u5219\u663E\u793A\uFF08v-auth-all="[xxx,xxx]"\uFF09\uFF1A'),ce={class:"flex-warp"},_e={class:"flex-warp-item"},re={class:"flex-warp-item-box"},fe=u("\u65B0\u589E "),he={class:"flex-warp-item"},me={class:"flex-warp-item-box"},xe=u("\u7F16\u8F91"),pe={class:"flex-warp-item"},Fe={class:"flex-warp-item-box"},be=u("\u5220\u9664 "),ve={class:"flex-warp-item"},Ae={class:"flex-warp-item-box"},we=u("\u8DF3\u8F6C "),Ce=u("auth('xxx')\u3001auths(['xxx','xxx'])\u3001authAll(['xxx','xxx'])\uFF1A"),ye={class:"flex-warp"},Be={class:"flex-warp-item"},ke={class:"flex-warp-item-box"},Ee=u("\u65B0\u589E "),ze={class:"flex-warp-item"},ge={class:"flex-warp-item-box"},De=u("\u7F16\u8F91"),Le={class:"flex-warp-item"},$e={class:"flex-warp-item-box"},Ie=u("\u5220\u9664 ");function Ue(o,n,d,i,f,h){const D=_("LimitsFrontEndPage"),c=_("el-row"),l=_("el-button"),m=_("Auth"),x=_("Auths"),p=_("AuthAll"),w=_("el-card"),F=z("auth"),b=z("auths"),v=z("auth-all");return I(),$("div",null,[e(D),e(w,{shadow:"hover",header:"\u6F14\u793A1\uFF1A\u7EC4\u4EF6\u65B9\u5F0F",class:"mt15"},{default:s(()=>[e(c,{class:"mb10",style:{color:"#808080"}},{default:s(()=>[O]),_:1}),t("div",Q,[e(m,{value:"btn.add"},{default:s(()=>[t("div",R,[t("div",W,[e(l,{type:"primary",size:"small",icon:"el-icon-document-add"},{default:s(()=>[X]),_:1})])])]),_:1},8,["value"]),e(m,{value:"btn.edit"},{default:s(()=>[t("div",Y,[t("div",Z,[e(l,{type:"info",size:"small",icon:"el-icon-edit-outline"},{default:s(()=>[tt]),_:1})])])]),_:1},8,["value"]),e(m,{value:"btn.del"},{default:s(()=>[t("div",et,[t("div",st,[e(l,{type:"danger",size:"small",icon:"el-icon-delete"},{default:s(()=>[ut]),_:1})])])]),_:1},8,["value"]),e(m,{value:"btn.link"},{default:s(()=>[t("div",lt,[t("div",ot,[e(l,{type:"success",size:"small",icon:"el-icon-link"},{default:s(()=>[it]),_:1})])])]),_:1},8,["value"])]),e(c,{class:"mb10 mt10",style:{color:"#808080"}},{default:s(()=>[nt]),_:1}),t("div",at,[e(x,{value:["btn.addsss","btn.edit","btn.delsss","btn.linksss"]},{default:s(()=>[t("div",dt,[t("div",ct,[e(l,{type:"primary",size:"small",icon:"el-icon-document-add"},{default:s(()=>[_t]),_:1})])])]),_:1},8,["value"]),e(x,{value:["btn.add","btn.edit","btn.del","btn.link"]},{default:s(()=>[t("div",rt,[t("div",ft,[e(l,{type:"info",size:"small",icon:"el-icon-edit-outline"},{default:s(()=>[ht]),_:1})])])]),_:1},8,["value"]),e(x,{value:["btn.add","btn.edit","btn.del","btn.link"]},{default:s(()=>[t("div",mt,[t("div",xt,[e(l,{type:"danger",size:"small",icon:"el-icon-delete"},{default:s(()=>[pt]),_:1})])])]),_:1},8,["value"]),e(x,{value:["btn.add","btn.edit","btn.del","btn.link"]},{default:s(()=>[t("div",Ft,[t("div",bt,[e(l,{type:"success",size:"small",icon:"el-icon-link"},{default:s(()=>[vt]),_:1})])])]),_:1},8,["value"])]),e(c,{class:"mb10 mt10",style:{color:"#808080"}},{default:s(()=>[At]),_:1}),t("div",wt,[e(p,{value:["btn.add","btn.edit","btn.del","btn.link"]},{default:s(()=>[t("div",Ct,[t("div",yt,[e(l,{type:"primary",size:"small",icon:"el-icon-document-add"},{default:s(()=>[Bt]),_:1})])])]),_:1},8,["value"]),e(p,{value:["btn.add","btn.edit","btn.del","btn.link"]},{default:s(()=>[t("div",kt,[t("div",Et,[e(l,{type:"info",size:"small",icon:"el-icon-edit-outline"},{default:s(()=>[zt]),_:1})])])]),_:1},8,["value"]),e(p,{value:["btn.add","btn.edit","btn.del","btn.link"]},{default:s(()=>[t("div",gt,[t("div",Dt,[e(l,{type:"danger",size:"small",icon:"el-icon-delete"},{default:s(()=>[Lt]),_:1})])])]),_:1},8,["value"]),e(p,{value:["btn.add","btn.edit","btn.del","btn.link"]},{default:s(()=>[t("div",$t,[t("div",It,[e(l,{type:"success",size:"small",icon:"el-icon-link"},{default:s(()=>[Ut]),_:1})])])]),_:1},8,["value"])])]),_:1}),e(w,{shadow:"hover",header:"\u6F14\u793A2\uFF1A\u6307\u4EE4\u65B9\u5F0F\uFF08\u9875\u9762\u521D\u59CB\u5316\u65F6\u6267\u884C\uFF09",class:"mt15"},{default:s(()=>[e(c,{class:"mb10",style:{color:"#808080"}},{default:s(()=>[jt]),_:1}),t("div",Nt,[a(t("div",St,[t("div",Vt,[e(l,{type:"primary",size:"small",icon:"el-icon-document-add"},{default:s(()=>[Pt]),_:1})])],512),[[F,"btn.add"]]),a(t("div",Jt,[t("div",Mt,[e(l,{type:"info",size:"small",icon:"el-icon-edit-outline"},{default:s(()=>[Tt]),_:1})])],512),[[F,"btn.edit"]]),a(t("div",qt,[t("div",Gt,[e(l,{type:"danger",size:"small",icon:"el-icon-delete"},{default:s(()=>[Ht]),_:1})])],512),[[F,"btn.del"]]),a(t("div",Kt,[t("div",Ot,[e(l,{type:"success",size:"small",icon:"el-icon-link"},{default:s(()=>[Qt]),_:1})])],512),[[F,"btn.link"]])]),e(c,{class:"mb10 mt10",style:{color:"#808080"}},{default:s(()=>[Rt]),_:1}),t("div",Wt,[a(t("div",Xt,[t("div",Yt,[e(l,{type:"primary",size:"small",icon:"el-icon-document-add"},{default:s(()=>[Zt]),_:1})])],512),[[b,["btn.addsss","btn.edit","btn.delsss","btn.linksss"]]]),a(t("div",te,[t("div",ee,[e(l,{type:"info",size:"small",icon:"el-icon-edit-outline"},{default:s(()=>[se]),_:1})])],512),[[b,["btn.add","btn.edit","btn.del","btn.link"]]]),a(t("div",ue,[t("div",le,[e(l,{type:"danger",size:"small",icon:"el-icon-delete"},{default:s(()=>[oe]),_:1})])],512),[[b,["btn.add","btn.edit","btn.del","btn.link"]]]),a(t("div",ie,[t("div",ne,[e(l,{type:"success",size:"small",icon:"el-icon-link"},{default:s(()=>[ae]),_:1})])],512),[[b,["btn.add","btn.edit","btn.del","btn.link"]]])]),e(c,{class:"mb10 mt10",style:{color:"#808080"}},{default:s(()=>[de]),_:1}),t("div",ce,[a(t("div",_e,[t("div",re,[e(l,{type:"primary",size:"small",icon:"el-icon-document-add"},{default:s(()=>[fe]),_:1})])],512),[[v,["btn.add","btn.edit","btn.del","btn.link"]]]),a(t("div",he,[t("div",me,[e(l,{type:"info",size:"small",icon:"el-icon-edit-outline"},{default:s(()=>[xe]),_:1})])],512),[[v,["btn.add","btn.edit","btn.del","btn.link"]]]),a(t("div",pe,[t("div",Fe,[e(l,{type:"danger",size:"small",icon:"el-icon-delete"},{default:s(()=>[be]),_:1})])],512),[[v,["btn.add","btn.edit","btn.del","btn.link"]]]),a(t("div",ve,[t("div",Ae,[e(l,{type:"success",size:"small",icon:"el-icon-link"},{default:s(()=>[we]),_:1})])],512),[[v,["btn.add","btn.edit","btn.del","btn.link"]]])])]),_:1}),e(w,{shadow:"hover",header:"\u6F14\u793A3\uFF1A\u51FD\u6570\u65B9\u5F0F\uFF08\u70B9\u51FB\u6309\u94AE\u67E5\u770B\u6709\u65E0\u6743\u9650\uFF0C\u7528\u4E8E\u5224\u65AD\uFF09",class:"mt15"},{default:s(()=>[e(c,{class:"mb10",style:{color:"#808080"}},{default:s(()=>[Ce]),_:1}),t("div",ye,[t("div",Be,[t("div",ke,[e(l,{type:"primary",size:"small",icon:"el-icon-document-add",onClick:i.onAuthClick},{default:s(()=>[Ee]),_:1},8,["onClick"])])]),t("div",ze,[t("div",ge,[e(l,{type:"info",size:"small",icon:"el-icon-edit-outline",onClick:i.onAuthsClick},{default:s(()=>[De]),_:1},8,["onClick"])])]),t("div",Le,[t("div",$e,[e(l,{type:"danger",size:"small",icon:"el-icon-delete",onClick:i.onAuthAllClick},{default:s(()=>[Ie]),_:1},8,["onClick"])])])])]),_:1})])}var Ve=A(K,[["render",Ue],["__scopeId","data-v-a0e4eb5a"]]);export{Ve as default};
