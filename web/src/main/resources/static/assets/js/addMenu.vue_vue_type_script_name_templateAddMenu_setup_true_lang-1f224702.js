import{_ as k}from"./index.vue_vue_type_script_setup_true_name_iconSelector_lang-cb8dc95c.js";import{T as C}from"./index-f07f4b76.js";import{a as D}from"./element-plus-91d6d961.js";import{H as F,e as I,_ as S,ah as r,o as M,c as A,V as e,P as a,a as B,T as V,n as i}from"./@vue-6dabbe94.js";const q={class:"dialog-footer"},z=F({name:"templateAddMenu"}),j=F({...z,emits:["reloadTable"],setup(E,{expose:x,emit:v}){const d=I(),_=C(),l=S({isShowDialog:!1,title:"",ruleForm:{id:"",parentId:"",menuName:"",menuUrl:"",menuIcon:"",sortNum:"",target:"_self",urlType:""},rules:{menuName:{required:!0,message:"请输入菜单名称",trigger:"blur"},menuUrl:{required:!0,message:"请输入菜单地址",trigger:"blur"}}}),T=(p,o)=>{p==="edit"?(l.title="修改菜单",_.getTemplateMenu(o.id).then(m=>{delete m.data.created,delete m.data.updated,l.ruleForm=m.data})):(l.title="新增菜单",i(()=>{d.value.resetFields(),l.ruleForm.id="",l.ruleForm.parentId=o==null?0:o.id||0})),l.isShowDialog=!0},c=()=>{l.isShowDialog=!1,i(()=>{d.value.resetFields()})},N=()=>{c(),f()},U=()=>{d.value.validate(p=>{p&&_.saveTemplateMenu(l.ruleForm).then(()=>{c(),f(),v("reloadTable")}).catch(o=>{D({showClose:!0,message:o.message?o.message:"系统错误",type:"error"})})})},f=()=>{i(()=>{d.value.resetFields()})};return x({openDialog:T}),(p,o)=>{const m=r("el-input"),u=r("el-form-item"),s=r("el-col"),n=r("el-option"),b=r("el-select"),w=r("el-row"),y=r("el-form"),g=r("el-button"),h=r("el-dialog");return M(),A("div",null,[e(h,{title:"新增菜单",modelValue:l.isShowDialog,"onUpdate:modelValue":o[6]||(o[6]=t=>l.isShowDialog=t),width:"769px"},{footer:a(()=>[B("span",q,[e(g,{onClick:N,size:"default"},{default:a(()=>[V("取 消")]),_:1}),e(g,{type:"primary",onClick:U,size:"default"},{default:a(()=>[V("保 存")]),_:1})])]),default:a(()=>[e(y,{model:l.ruleForm,"label-width":"80px",rules:l.rules,ref_key:"myRefForm",ref:d},{default:a(()=>[e(w,{gutter:35},{default:a(()=>[e(s,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:a(()=>[e(u,{label:"菜单名称",prop:"menuName"},{default:a(()=>[e(m,{modelValue:l.ruleForm.menuName,"onUpdate:modelValue":o[0]||(o[0]=t=>l.ruleForm.menuName=t),placeholder:"请输入菜单名称",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(s,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:a(()=>[e(u,{label:"菜单",prop:"menuIcon"},{default:a(()=>[e(k,{placeholder:"请输入菜单图标",modelValue:l.ruleForm.menuIcon,"onUpdate:modelValue":o[1]||(o[1]=t=>l.ruleForm.menuIcon=t)},null,8,["modelValue"])]),_:1})]),_:1}),e(s,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:a(()=>[e(u,{label:"关联",prop:"urlType"},{default:a(()=>[e(b,{modelValue:l.ruleForm.urlType,"onUpdate:modelValue":o[2]||(o[2]=t=>l.ruleForm.urlType=t),placeholder:"请选择",clearable:"",class:"w100"},{default:a(()=>[e(n,{label:"网址",value:0}),e(n,{label:"文章",value:1}),e(n,{label:"页面",value:2}),e(n,{label:"分类",value:3})]),_:1},8,["modelValue"])]),_:1})]),_:1}),e(s,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:a(()=>[e(u,{label:"菜单地址",prop:"menuUrl"},{default:a(()=>[e(m,{modelValue:l.ruleForm.menuUrl,"onUpdate:modelValue":o[3]||(o[3]=t=>l.ruleForm.menuUrl=t),placeholder:"请输入菜单地址",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(s,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:a(()=>[e(u,{label:"排序",prop:"sortNum"},{default:a(()=>[e(m,{type:"number",modelValue:l.ruleForm.sortNum,"onUpdate:modelValue":o[4]||(o[4]=t=>l.ruleForm.sortNum=t),placeholder:"排序",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(s,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:a(()=>[e(u,{label:"打开方式",prop:"target"},{default:a(()=>[e(b,{modelValue:l.ruleForm.target,"onUpdate:modelValue":o[5]||(o[5]=t=>l.ruleForm.target=t),placeholder:"请选择打开方式",clearable:"",class:"w100"},{default:a(()=>[e(n,{label:"本窗口",value:"_self"}),e(n,{label:"新开窗口",value:"_blank"})]),_:1},8,["modelValue"])]),_:1})]),_:1})]),_:1})]),_:1},8,["model","rules"])]),_:1},8,["modelValue"])])}}});export{j as _};