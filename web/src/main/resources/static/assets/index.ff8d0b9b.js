var F=Object.defineProperty;var y=Object.getOwnPropertySymbols;var x=Object.prototype.hasOwnProperty,A=Object.prototype.propertyIsEnumerable;var b=(a,t,n)=>t in a?F(a,t,{enumerable:!0,configurable:!0,writable:!0,value:n}):a[t]=n,v=(a,t)=>{for(var n in t||(t={}))x.call(t,n)&&b(a,n,t[n]);if(y)for(var n of y(t))A.call(t,n)&&b(a,n,t[n]);return a};import{_ as B,u as R}from"./index.fabd0e58.js";import T from"./addMenu.8d00494b.js";import g from"./editMenu.067e4e96.js";import{r as E,j,J as O,t as z,l as d,m as u,z as o,A as s,E as L,D as i,p,q as S,y as m,x as f}from"./vendor.09ddcd37.js";import"./index.a05fd9c4.js";import"./getStyleSheets.0836c143.js";const $={name:"systemMenu",components:{AddMenu:T,EditMenu:g},setup(){const a=R(),t=E(),n=E(),c=j({}),h=O(()=>a.state.routesList.routesList);return v({addMenuRef:t,editMenuRef:n,onOpenAddMenu:r=>{t.value.openDialog(r)},onOpenEditMenu:r=>{n.value.openDialog(r)},menuTableData:h,onTabelRowDel:r=>{L.confirm("\u6B64\u64CD\u4F5C\u5C06\u6C38\u4E45\u5220\u9664\u8DEF\u7531, \u662F\u5426\u7EE7\u7EED?","\u63D0\u793A",{confirmButtonText:"\u5220\u9664",cancelButtonText:"\u53D6\u6D88",type:"warning"}).then(()=>{console.log(r)}).catch(()=>{})}},z(c))}},N={class:"system-menu-container"},V={class:"ml10"},I={key:0,class:"color-primary"},q={key:1,class:"color-info"},H={key:0,class:"color-primary"},J={key:1,class:"color-info"},K={key:0,class:"color-primary"},G={key:1,class:"color-info"},P={key:0,class:"color-primary"},Q={key:1,class:"color-info"},U={key:0,class:"color-primary"},W={key:1,class:"color-info"},X=f("\u65B0\u589E"),Y=f("\u4FEE\u6539"),Z=f("\u5220\u9664");function ee(a,t,n,c,h,k){const l=d("el-table-column"),_=d("el-button"),r=d("el-table"),C=d("el-card"),D=d("AddMenu"),M=d("EditMenu");return i(),u("div",N,[o(C,{shadow:"hover"},{default:s(()=>[o(r,{data:c.menuTableData,stripe:"",style:{width:"100%"},"row-key":"path","tree-props":{children:"children",hasChildren:"hasChildren"}},{default:s(()=>[o(l,{label:"\u83DC\u5355\u540D\u79F0","show-overflow-tooltip":""},{default:s(e=>[p("i",{class:S(e.row.meta.icon)},null,2),p("span",V,m(a.$t(e.row.meta.title)),1)]),_:1}),o(l,{prop:"path",label:"\u8DEF\u7531\u540D\u79F0","show-overflow-tooltip":"",width:"150"}),o(l,{label:"\u7EC4\u4EF6\u5730\u5740","show-overflow-tooltip":""},{default:s(e=>[p("span",null,m(e.row.component),1)]),_:1}),o(l,{label:"\u9690\u85CF","show-overflow-tooltip":"",width:"70"},{default:s(e=>[e.row.meta.isHide?(i(),u("span",I,"\u662F")):(i(),u("span",q,"\u5426"))]),_:1}),o(l,{label:"\u7F13\u5B58","show-overflow-tooltip":"",width:"70"},{default:s(e=>[e.row.meta.isKeepAlive?(i(),u("span",H,"\u662F")):(i(),u("span",J,"\u5426"))]),_:1}),o(l,{label:"\u56FA\u5B9A","show-overflow-tooltip":"",width:"70"},{default:s(e=>[e.row.meta.isAffix?(i(),u("span",K,"\u662F")):(i(),u("span",G,"\u5426"))]),_:1}),o(l,{label:"\u5916\u94FE","show-overflow-tooltip":"",width:"70"},{default:s(e=>[e.row.meta.isLink&&!e.row.meta.isIframe?(i(),u("span",P,"\u662F")):(i(),u("span",Q,"\u5426"))]),_:1}),o(l,{label:"iframe","show-overflow-tooltip":"",width:"70"},{default:s(e=>[e.row.meta.isLink&&e.row.meta.isIframe?(i(),u("span",U,"\u662F")):(i(),u("span",W,"\u5426"))]),_:1}),o(l,{label:"\u6743\u9650\u6807\u8BC6","show-overflow-tooltip":""},{default:s(e=>[p("span",null,m(e.row.meta.auth),1)]),_:1}),o(l,{label:"\u64CD\u4F5C","show-overflow-tooltip":"",width:"125"},{default:s(e=>[o(_,{size:"mini",type:"text",onClick:w=>c.onOpenAddMenu(e.row)},{default:s(()=>[X]),_:2},1032,["onClick"]),o(_,{size:"mini",type:"text",onClick:w=>c.onOpenEditMenu(e.row)},{default:s(()=>[Y]),_:2},1032,["onClick"]),o(_,{size:"mini",type:"text",onClick:w=>c.onTabelRowDel(e.row)},{default:s(()=>[Z]),_:2},1032,["onClick"])]),_:1})]),_:1},8,["data"])]),_:1}),o(D,{ref:"addMenuRef"},null,512),o(M,{ref:"editMenuRef"},null,512)])}var ie=B($,[["render",ee]]);export{ie as default};
