import{_ as D}from"./index.vue_vue_type_script_setup_true_name_iconSelector_lang-8a61470c.js";import{A as v}from"./index-c5d63bb6.js";import{a as A}from"./element-plus-91d6d961.js";import{H as _,e as S,_ as T,ah as s,o as k,c as U,V as l,P as o,a as g,T as f}from"./@vue-6dabbe94.js";const z=g("div",{class:"sub-title"},"结合网站模板使用",-1),B={class:"dialog-footer"},E=_({name:"articleAddTag"}),P=_({...E,emits:["reloadTable"],setup(I,{expose:b,emit:x}){const F=v(),d=S(),e=T({isShowDialog:!1,ruleForm:{id:"",tagName:"",suffix:"",icon:"",sortNum:"",parentId:0},rules:{tagName:{required:!0,message:"请输入标签名称",trigger:"blur"}}}),V=r=>{console.log(r),e.isShowDialog=!0,r&&(e.ruleForm.parentId=r.id)},i=r=>{console.log(r),e.isShowDialog=!1},N=()=>{i(),c()},h=()=>{d.value.validate(r=>{r&&F.addArticleTag(e.ruleForm).then(()=>{i(),c(),x("reloadTable")}).catch(a=>{A({showClose:!0,message:a.message?a.message:"系统错误",type:"error"})})})},c=()=>{e.ruleForm.id="",e.ruleForm.tagName="",e.ruleForm.icon="",e.ruleForm.suffix="",e.ruleForm.sortNum=""};return b({openDialog:V}),(r,a)=>{const n=s("el-input"),m=s("el-form-item"),u=s("el-col"),w=s("el-row"),y=s("el-form"),p=s("el-button"),C=s("el-dialog");return k(),U("div",null,[l(C,{title:"新增标签",modelValue:e.isShowDialog,"onUpdate:modelValue":a[4]||(a[4]=t=>e.isShowDialog=t),width:"769px"},{footer:o(()=>[g("span",B,[l(p,{onClick:N,size:"small"},{default:o(()=>[f("取 消")]),_:1}),l(p,{type:"primary",onClick:h,size:"small"},{default:o(()=>[f("新 增")]),_:1})])]),default:o(()=>[l(y,{model:e.ruleForm,size:"small","label-width":"80px",rules:e.rules,ref_key:"myRefForm",ref:d},{default:o(()=>[l(w,{gutter:35},{default:o(()=>[l(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[l(m,{label:"标签名称",prop:"tagName"},{default:o(()=>[l(n,{modelValue:e.ruleForm.tagName,"onUpdate:modelValue":a[0]||(a[0]=t=>e.ruleForm.tagName=t),placeholder:"请输入标签名称",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),l(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[l(m,{label:"标签图标",prop:"icon"},{default:o(()=>[l(D,{placeholder:"请输入标签图标",modelValue:e.ruleForm.icon,"onUpdate:modelValue":a[1]||(a[1]=t=>e.ruleForm.icon=t)},null,8,["modelValue"])]),_:1})]),_:1}),l(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[l(m,{label:"模板",prop:"suffix"},{default:o(()=>[l(n,{modelValue:e.ruleForm.suffix,"onUpdate:modelValue":a[2]||(a[2]=t=>e.ruleForm.suffix=t),placeholder:"请输入模板后缀名称",clearable:""},null,8,["modelValue"]),z]),_:1})]),_:1}),l(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[l(m,{label:"排序",prop:"sortNum"},{default:o(()=>[l(n,{type:"number",modelValue:e.ruleForm.sortNum,"onUpdate:modelValue":a[3]||(a[3]=t=>e.ruleForm.sortNum=t),placeholder:"排序",clearable:""},null,8,["modelValue"])]),_:1})]),_:1})]),_:1})]),_:1},8,["model","rules"])]),_:1},8,["modelValue"])])}}});export{P as _};
