import{T}from"./index-a011cffb.js";import{_ as x}from"./addMenu.vue_vue_type_script_name_templateAddMenu_setup_true_lang-2f8fde50.js";import{E as g,a as f}from"./element-plus-91d6d961.js";import{H as h,e as D,_ as B,j as E,i as z,ah as l,o as N,c as R,V as e,P as o,T as r}from"./@vue-6dabbe94.js";import"./index-0ad00899.js";import"./pinia-b6f74250.js";import"./vue-router-c1461dfc.js";import"./vue-i18n-b3581196.js";import"./@intlify-ea47d1db.js";import"./source-map-7d7e1c08.js";import"./vue-b1ba6f14.js";import"./js-cookie-edb2da2a.js";import"./@element-plus-64e6e14a.js";import"./nprogress-08a53ce8.js";import"./axios-4a70c6fc.js";import"./qs-c5b6dbf2.js";import"./side-channel-394f276c.js";import"./get-intrinsic-bd2830fd.js";import"./has-symbols-e8f3ca0e.js";import"./has-proto-f7d0b240.js";import"./function-bind-22e7ee79.js";import"./has-26d28e02.js";import"./call-bind-e5c1c8b0.js";import"./object-inspect-8fd4bade.js";import"./mitt-f7ef348c.js";import"./vue-grid-layout-ec2a62c3.js";import"./lodash-es-9851428c.js";import"./@vueuse-e129c873.js";import"./@popperjs-c75af06c.js";import"./@ctrl-f8748455.js";import"./dayjs-f6fdf7b4.js";import"./async-validator-dee29e8b.js";import"./memoize-one-297ddbcb.js";import"./escape-html-b8998962.js";import"./normalize-wheel-es-ed76fb12.js";import"./@floating-ui-880a26aa.js";import"./index.vue_vue_type_script_setup_true_name_iconSelector_lang-8a61470c.js";const $=h({name:"templateMenu"}),ve=h({...$,setup(A){const s=T(),i=D(),c=B({menuData:null}),w=E(()=>c.menuData),u=t=>{i.value.openDialog("add",t)},v=t=>{i.value.openDialog("edit",t)},b=t=>{g.confirm("此操作将永久删除菜单, 是否继续?","提示",{confirmButtonText:"删除",cancelButtonText:"取消",type:"warning"}).then(()=>{s.delTemplateMenu(t.id).then(()=>{f.success("删除成功"),p()}).catch(d=>{f.error(d.message)})}).catch(()=>{})},p=()=>{s.getTemplateMenuList().then(t=>{c.menuData=t.data}).catch(()=>{})};return z(()=>{p()}),(t,d)=>{const y=l("ele-Plus"),C=l("el-icon"),n=l("el-button"),a=l("el-table-column"),k=l("el-table"),M=l("el-card");return N(),R("div",null,[e(M,{shadow:"hover"},{default:o(()=>[e(n,{onClick:u,class:"mt15",size:"default",type:"primary"},{default:o(()=>[e(C,null,{default:o(()=>[e(y)]),_:1}),r("新建菜单")]),_:1}),e(k,{data:w.value,stripe:"",style:{width:"100%"},"row-key":"id","tree-props":{children:"children",hasChildren:"hasChildren"}},{default:o(()=>[e(a,{prop:"menuName",label:"名称","show-overflow-tooltip":""}),e(a,{prop:"menuUrl",label:"跳转地址","show-overflow-tooltip":""}),e(a,{prop:"sortNum",label:"排序","show-overflow-tooltip":""}),e(a,{prop:"target",label:"打开方式","show-overflow-tooltip":""}),e(a,{label:"操作","show-overflow-tooltip":"",width:"160"},{default:o(m=>[e(n,{size:"small",text:"",type:"primary",onClick:_=>u(m.row)},{default:o(()=>[r("新增")]),_:2},1032,["onClick"]),e(n,{size:"small",text:"",type:"primary",onClick:_=>v(m.row)},{default:o(()=>[r("修改")]),_:2},1032,["onClick"]),e(n,{size:"small",text:"",type:"primary",onClick:_=>b(m.row)},{default:o(()=>[r("删除")]),_:2},1032,["onClick"])]),_:1})]),_:1},8,["data"])]),_:1}),e(x,{ref_key:"addMenuRef",ref:i,onReloadTable:p},null,512)])}}});export{ve as default};
