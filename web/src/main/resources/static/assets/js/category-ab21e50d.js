import{A as x}from"./index-c5d63bb6.js";import{_ as D}from"./addCategory.vue_vue_type_script_setup_true_name_articleAddCategory_lang-5b1595bd.js";import{E as A,a as f}from"./element-plus-91d6d961.js";import{H as h,e as T,_ as B,j as E,i as z,ah as l,o as N,c as R,V as t,P as o,T as n,a as V,U as $}from"./@vue-6dabbe94.js";import"./index-0ad00899.js";import"./pinia-b6f74250.js";import"./vue-router-c1461dfc.js";import"./vue-i18n-b3581196.js";import"./@intlify-ea47d1db.js";import"./source-map-7d7e1c08.js";import"./vue-b1ba6f14.js";import"./js-cookie-edb2da2a.js";import"./@element-plus-64e6e14a.js";import"./nprogress-08a53ce8.js";import"./axios-4a70c6fc.js";import"./qs-c5b6dbf2.js";import"./side-channel-394f276c.js";import"./get-intrinsic-bd2830fd.js";import"./has-symbols-e8f3ca0e.js";import"./has-proto-f7d0b240.js";import"./function-bind-22e7ee79.js";import"./has-26d28e02.js";import"./call-bind-e5c1c8b0.js";import"./object-inspect-8fd4bade.js";import"./mitt-f7ef348c.js";import"./vue-grid-layout-ec2a62c3.js";import"./lodash-es-9851428c.js";import"./@vueuse-e129c873.js";import"./@popperjs-c75af06c.js";import"./@ctrl-f8748455.js";import"./dayjs-f6fdf7b4.js";import"./async-validator-dee29e8b.js";import"./memoize-one-297ddbcb.js";import"./escape-html-b8998962.js";import"./normalize-wheel-es-ed76fb12.js";import"./@floating-ui-880a26aa.js";import"./index-585f215c.js";import"./index-e249eaf4.js";import"./client-a6a88275.js";import"./_plugin-vue_export-helper-c27b6911.js";const M={class:"system-Category-container"},P=["href"],L=h({name:"articleCategory"}),Dt=h({...L,setup(O){const m=x(),p=T(),c=B({menuData:null}),w=E(()=>c.menuData),d=e=>{p.value.openDialog("add",e)},y=e=>{p.value.openDialog("edit",e)},C=e=>{A.confirm("此操作将永久删除分类, 是否继续?","提示",{confirmButtonText:"删除",cancelButtonText:"取消",type:"warning"}).then(()=>{m.delArticleCategory(e.id).then(()=>{f.success("删除成功"),s()}).catch(_=>{f.error(_.message)})}).catch(()=>{})},s=()=>{m.getArticleCategoryList().then(e=>{c.menuData=e.data}).catch(()=>{})};return z(()=>{s()}),(e,_)=>{const g=l("ele-Plus"),b=l("el-icon"),i=l("el-button"),r=l("el-table-column"),v=l("el-table"),k=l("el-card");return N(),R("div",M,[t(k,{shadow:"hover"},{default:o(()=>[t(i,{onClick:d,size:"default",type:"primary"},{default:o(()=>[t(b,null,{default:o(()=>[t(g)]),_:1}),n("新建分类")]),_:1}),t(v,{data:w.value,stripe:"",style:{width:"100%"},"row-key":"id","tree-props":{children:"children",hasChildren:"hasChildren"}},{default:o(()=>[t(r,{prop:"title",label:"名称","show-overflow-tooltip":""},{default:o(a=>[V("a",{href:a.row.url,target:"_blank"},$(a.row.title),9,P)]),_:1}),t(r,{prop:"id",label:"id","show-overflow-tooltip":"",width:"60"}),t(r,{prop:"path",label:"路径","show-overflow-tooltip":""}),t(r,{prop:"suffix",label:"模板","show-overflow-tooltip":""}),t(r,{prop:"sortNum",label:"排序","show-overflow-tooltip":""}),t(r,{prop:"created",label:"创建时间","show-overflow-tooltip":""}),t(r,{label:"操作","show-overflow-tooltip":"",width:"160"},{default:o(a=>[t(i,{size:"small",text:"",type:"primary",onClick:u=>d(a.row)},{default:o(()=>[n("新增")]),_:2},1032,["onClick"]),t(i,{size:"small",text:"",type:"primary",onClick:u=>y(a.row)},{default:o(()=>[n("修改")]),_:2},1032,["onClick"]),t(i,{size:"small",text:"",type:"primary",onClick:u=>C(a.row)},{default:o(()=>[n("删除")]),_:2},1032,["onClick"])]),_:1})]),_:1},8,["data"])]),_:1}),t(D,{ref_key:"addCategoryRef",ref:p,onReloadTable:s},null,512)])}}});export{Dt as default};
