var D=Object.defineProperty;var m=Object.getOwnPropertySymbols;var v=Object.prototype.hasOwnProperty,x=Object.prototype.propertyIsEnumerable;var g=(a,o,e)=>o in a?D(a,o,{enumerable:!0,configurable:!0,writable:!0,value:e}):a[o]=e,y=(a,o)=>{for(var e in o||(o={}))v.call(o,e)&&g(a,e,o[e]);if(m)for(var e of m(o))x.call(o,e)&&g(a,e,o[e]);return a};import{d as F,g as A}from"./index.7d847a29.js";import k from"./addCategory.f4af4418.js";import B from"./editCategory.a74ff954.js";import{_ as R}from"./index.9d91ddd7.js";import{r as h,j as T,J as j,o as z,t as O,l as u,m as L,z as t,A as s,E as N,b as w,D as M,p as V,y as J,x as c}from"./vendor.d2ed1f2c.js";import"./index.eb3d5054.js";const S={name:"articleCategory",components:{AddCategory:k,EditCategory:B},setup(){const a=h(),o=h(),e=T({menuData:null}),r=j(()=>e.menuData),_=l=>{a.value.openDialog(l)},f=l=>{o.value.openDialog(l)},d=l=>{N.confirm("\u6B64\u64CD\u4F5C\u5C06\u6C38\u4E45\u5220\u9664\u83DC\u5355, \u662F\u5426\u7EE7\u7EED?","\u63D0\u793A",{confirmButtonText:"\u5220\u9664",cancelButtonText:"\u53D6\u6D88",type:"warning"}).then(()=>{console.log(l),F(l.id).then(()=>{w.success("\u5220\u9664\u6210\u529F"),n()}).catch(p=>{w.error(p.message)})}).catch(()=>{})},n=()=>{A().then(l=>{e.menuData=l.data}).catch(()=>{})};return z(()=>{n()}),y({addCategoryRef:a,editCategoryRef:o,onOpenAddCategory:_,onOpenEditCategory:f,menuTableData:r,onTabelRowDel:d,loadCategoryList:n},O(e))}},q={class:"system-Category-container"},G=c("\u65B0\u5EFA\u5206\u7C7B"),H=["href"],I=c("\u65B0\u589E"),K=c("\u4FEE\u6539"),P=c("\u5220\u9664");function Q(a,o,e,r,_,f){const d=u("el-button"),n=u("el-table-column"),l=u("el-table"),p=u("el-card"),b=u("AddCategory"),E=u("EditCategory");return M(),L("div",q,[t(p,{shadow:"hover"},{default:s(()=>[t(d,{onClick:r.onOpenAddCategory,class:"mt15",size:"small",type:"primary",icon:"iconfont icon-shuxingtu"},{default:s(()=>[G]),_:1},8,["onClick"]),t(l,{data:r.menuTableData,stripe:"",style:{width:"100%"},"row-key":"id","tree-props":{children:"children",hasChildren:"hasChildren"}},{default:s(()=>[t(n,{prop:"title",label:"\u540D\u79F0","show-overflow-tooltip":""},{default:s(i=>[V("a",{href:i.row.url,target:"_blank"},J(i.row.title),9,H)]),_:1}),t(n,{prop:"sortNum",label:"\u6392\u5E8F","show-overflow-tooltip":""}),t(n,{prop:"path",label:"\u8DEF\u5F84","show-overflow-tooltip":""}),t(n,{prop:"suffix",label:"\u6A21\u677F","show-overflow-tooltip":""}),t(n,{prop:"created",label:"\u521B\u5EFA\u65F6\u95F4","show-overflow-tooltip":""}),t(n,{label:"\u64CD\u4F5C","show-overflow-tooltip":"",width:"125"},{default:s(i=>[t(d,{size:"mini",type:"text",onClick:C=>r.onOpenAddCategory(i.row)},{default:s(()=>[I]),_:2},1032,["onClick"]),t(d,{size:"mini",type:"text",onClick:C=>r.onOpenEditCategory(i.row)},{default:s(()=>[K]),_:2},1032,["onClick"]),t(d,{size:"mini",type:"text",onClick:C=>r.onTabelRowDel(i.row)},{default:s(()=>[P]),_:2},1032,["onClick"])]),_:1})]),_:1},8,["data"])]),_:1}),t(b,{ref:"addCategoryRef",onReloadTable:r.loadCategoryList},null,8,["onReloadTable"]),t(E,{ref:"editCategoryRef",onReloadTable:r.loadCategoryList},null,8,["onReloadTable"])])}var oe=R(S,[["render",Q]]);export{oe as default};
