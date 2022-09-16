var z=Object.defineProperty;var b=Object.getOwnPropertySymbols;var E=Object.prototype.hasOwnProperty,V=Object.prototype.propertyIsEnumerable;var c=(e,a,o)=>a in e?z(e,a,{enumerable:!0,configurable:!0,writable:!0,value:o}):e[a]=o,D=(e,a)=>{for(var o in a||(a={}))E.call(a,o)&&c(e,o,a[o]);if(b)for(var o of b(a))V.call(a,o)&&c(e,o,a[o]);return e};import{b as y}from"./index.cd15f066.js";import S from"./detail.c60167da.js";import{_ as F}from"./index.9d91ddd7.js";import{r as A,j as k,o as x,t as N,l as u,m as R,z as l,A as r,D as _,p as T,Q as I,L as j,x as f}from"./vendor.d2ed1f2c.js";const H={name:"orderManager",components:{Detail:S},setup(){const e=A(),a=k({fit:"fill",queryParams:{},showSearch:!0,tableData:{data:[],total:0,loading:!1,param:{pageNum:1,pageSize:10}}}),o=()=>{y(a.tableData.param).then(t=>{a.tableData.data=t.data.records,a.tableData.total=t.data.total})};return x(()=>{o()}),D({detailRef:e,initTableData:o,onRowInfo:t=>{e.value.openDialog(t.id)},onTableItemClick:t=>{e.value.openDialog(t.id)},onHandleSizeChange:t=>{a.tableData.param.pageSize=t,o()},onHandleCurrentChange:t=>{a.tableData.param.pageNum=t,o()}},N(a))}},U={class:"list-adapt-container"},L={class:"mb15"},M=f("\u67E5\u8BE2"),q=f("\u8BE6\u60C5");function O(e,a,o,d,g,h){const p=u("el-input"),t=u("el-option"),i=u("el-select"),m=u("el-button"),s=u("el-table-column"),v=u("el-table"),C=u("el-pagination"),w=u("el-card"),B=u("Detail");return _(),R("div",U,[l(w,{shadow:"hover"},{default:r(()=>[T("div",L,[l(p,{size:"small",modelValue:e.tableData.param.orderSn,"onUpdate:modelValue":a[0]||(a[0]=n=>e.tableData.param.orderSn=n),placeholder:"\u8BA2\u5355\u7F16\u53F7",style:{"max-width":"180px"},class:"ml10"},null,8,["modelValue"]),l(p,{size:"small",modelValue:e.tableData.param.title,"onUpdate:modelValue":a[1]||(a[1]=n=>e.tableData.param.title=n),placeholder:"\u5546\u54C1\u540D\u79F0",style:{"max-width":"180px"},class:"ml10"},null,8,["modelValue"]),l(i,{size:"small",style:{"max-width":"180px"},modelValue:e.tableData.param.payStatus,"onUpdate:modelValue":a[2]||(a[2]=n=>e.tableData.param.payStatus=n),placeholder:"\u652F\u4ED8\u72B6\u6001",clearable:"",class:"ml10"},{default:r(()=>[l(t,{label:"\u5DF2\u652F\u4ED8",value:"1"}),l(t,{label:"\u672A\u652F\u4ED8",value:"0"})]),_:1},8,["modelValue"]),l(i,{size:"small",style:{"max-width":"180px"},modelValue:e.tableData.param.tradeStatus,"onUpdate:modelValue":a[3]||(a[3]=n=>e.tableData.param.tradeStatus=n),placeholder:"\u4EA4\u6613\u72B6\u6001",clearable:"",class:"ml10"},{default:r(()=>[l(t,{label:"\u4EA4\u6613\u4E2D",value:"1"}),l(t,{label:"\u4EA4\u6613\u5B8C\u6210(\u53EF\u9000\u6B3E)",value:"2"}),l(t,{label:"\u53D6\u6D88\u4EA4\u6613",value:"3"}),l(t,{label:"\u4EA4\u6613\u5B8C\u6210",value:"8"}),l(t,{label:"\u8BA2\u5355\u5173\u95ED",value:"9"})]),_:1},8,["modelValue"]),l(i,{size:"small",style:{"max-width":"180px"},modelValue:e.tableData.param.status,"onUpdate:modelValue":a[4]||(a[4]=n=>e.tableData.param.status=n),placeholder:"\u8BA2\u5355\u72B6\u6001",clearable:"",class:"ml10"},{default:r(()=>[l(t,{label:"\u6B63\u5E38",value:"1"}),l(t,{label:"\u5220\u9664",value:"0"})]),_:1},8,["modelValue"]),l(m,{size:"small",type:"primary",class:"ml10",onClick:d.initTableData},{default:r(()=>[M]),_:1},8,["onClick"])]),l(v,{data:e.tableData.data,stripe:"",style:{width:"100%"}},{default:r(()=>[l(s,{prop:"id",label:"ID","show-overflow-tooltip":""}),l(s,{prop:"orderSn",label:"\u8BA2\u5355\u7F16\u53F7","show-overflow-tooltip":""}),l(s,{prop:"title",label:"\u5546\u54C1","show-overflow-tooltip":""}),l(s,{prop:"totalAmount",label:"\u91D1\u989D","show-overflow-tooltip":""}),l(s,{prop:"userName",label:"\u4E70\u5BB6","show-overflow-tooltip":""}),l(s,{prop:"payStatusStr",label:"\u652F\u4ED8\u72B6\u6001","show-overflow-tooltip":""}),l(s,{prop:"created",label:"\u521B\u5EFA\u65F6\u95F4","show-overflow-tooltip":""}),l(s,{prop:"path",label:"\u64CD\u4F5C",width:"90"},{default:r(n=>[l(m,{size:"mini",type:"text",onClick:P=>d.onRowInfo(n.row)},{default:r(()=>[q]),_:2},1032,["onClick"])]),_:1})]),_:1},8,["data"]),e.tableData.data.length>0?(_(),I(C,{key:0,style:{"text-align":"right"},background:"",onSizeChange:d.onHandleSizeChange,onCurrentChange:d.onHandleCurrentChange,"page-sizes":[10,20,30],"current-page":e.tableData.param.pageNum,"page-size":e.tableData.param.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.tableData.total},null,8,["onSizeChange","onCurrentChange","current-page","page-size","total"])):j("",!0)]),_:1}),l(B,{ref:"detailRef",onReloadTable:d.initTableData},null,8,["onReloadTable"])])}var X=F(H,[["render",O],["__scopeId","data-v-b8ae2da4"]]);export{X as default};
