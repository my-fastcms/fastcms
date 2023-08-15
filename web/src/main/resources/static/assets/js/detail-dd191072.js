import{C as O}from"./client-f002bed1.js";import{Q as M}from"./qrcodejs2-fixes-aebf80af.js";import{a as q}from"./element-plus-91d6d961.js";import{H as k,_ as A,a0 as L,ah as m,o as d,c as v,V as o,P as t,a as s,O as b,T as p,S as F,U as a,F as B,a8 as R,bh as P,bf as U,h as z}from"./@vue-6dabbe94.js";import{_ as E}from"./_plugin-vue_export-helper-c27b6911.js";import"./index-0ad00899.js";import"./pinia-b6f74250.js";import"./vue-router-c1461dfc.js";import"./vue-i18n-b3581196.js";import"./@intlify-ea47d1db.js";import"./source-map-7d7e1c08.js";import"./vue-b1ba6f14.js";import"./js-cookie-edb2da2a.js";import"./@element-plus-64e6e14a.js";import"./nprogress-08a53ce8.js";import"./axios-4a70c6fc.js";import"./qs-c5b6dbf2.js";import"./side-channel-394f276c.js";import"./get-intrinsic-bd2830fd.js";import"./has-symbols-e8f3ca0e.js";import"./has-proto-f7d0b240.js";import"./function-bind-22e7ee79.js";import"./has-26d28e02.js";import"./call-bind-e5c1c8b0.js";import"./object-inspect-8fd4bade.js";import"./mitt-f7ef348c.js";import"./vue-grid-layout-ec2a62c3.js";import"./lodash-es-9851428c.js";import"./@vueuse-e129c873.js";import"./@popperjs-c75af06c.js";import"./@ctrl-f8748455.js";import"./dayjs-f6fdf7b4.js";import"./async-validator-dee29e8b.js";import"./memoize-one-297ddbcb.js";import"./escape-html-b8998962.js";import"./normalize-wheel-es-ed76fb12.js";import"./@floating-ui-880a26aa.js";const r=c=>(P("data-v-a90dd9f5"),c=c(),U(),c),H={class:"personal"},Q={class:"personal-user"},$={class:"personal-user-right"},X=r(()=>s("div",{class:"personal-item-label"},"订单编号：",-1)),j={class:"personal-item-value"},G=r(()=>s("div",{class:"personal-item-label"},"订单状态：",-1)),J={class:"personal-item-value"},K=r(()=>s("div",{class:"personal-item-label"},"交易状态：",-1)),W={class:"personal-item-value"},Y=r(()=>s("div",{class:"personal-item-label"},"支付状态：",-1)),Z={class:"personal-item-value"},ee=r(()=>s("div",{class:"personal-item-label"},"订单金额：",-1)),te={class:"personal-item-value"},se=r(()=>s("div",{class:"personal-item-label"},"买家：",-1)),oe={class:"personal-item-value"},le=r(()=>s("div",{class:"personal-item-label"},"创建时间：",-1)),ae={class:"personal-item-value"},re=r(()=>s("div",{class:"personal-item-label"},"买家留言：",-1)),ie={class:"personal-item-value"},ne=r(()=>s("div",{class:"personal-item-label"},"卖家备注：",-1)),de={class:"personal-item-value"},me={class:"personal-edit-safe-item"},ce={class:"personal-edit-safe-item-left"},ue={class:"personal-edit-safe-item-left-label"},_e={class:"personal-edit-safe-item-right"},pe={class:"mb30 mt30 qrcode-img"},fe={class:"qrcode",ref:"qrcodeRef"},he={class:"dialog-footer"},ve=k({name:"attachDetail"}),be=k({...ve,setup(c,{expose:w}){const{proxy:g}=z(),f=O(),e=A({isShowDialog:!1,myTimer:null,ruleForm:{id:null,orderTitle:"",nickName:"",orderSn:"",statusStr:"",tradeStatusStr:"",payStatus:1,payStatusStr:"",orderAmount:"",payAmount:"",userName:"",created:"",buyerMsg:"",remarks:"",orderItemList:[],status:1},rules:{}}),C=i=>{e.isShowDialog=!0,e.ruleForm.id=i},y=()=>{e.isShowDialog=!1,e.myTimer!=null&&clearInterval(Number(e.myTimer))},D=()=>{y(),S()},T=()=>{e.ruleForm.id&&e.ruleForm.id!=null&&f.getOrderDetail(e.ruleForm.id).then(i=>{e.ruleForm=i.data}).catch(i=>{q.error(i)})},I=()=>{f.paymentOrder(e.ruleForm.id).then(i=>{g.$refs.qrcodeRef.innerHTML="",new M(g.$refs.qrcodeRef,{text:i,width:125,height:125,colorDark:"#000000",colorLight:"#ffffff"}),e.myTimer=setInterval(()=>{e.ruleForm.id&&f.checkOrderPayStatus(e.ruleForm.id).then(u=>{u.code==200&&(y(),S())}).catch(()=>{})},2e3)})};L(()=>{T()});const S=()=>{e.ruleForm={}};return w({openDialog:C}),(i,u)=>{const l=m("el-col"),n=m("el-row"),x=m("el-card"),h=m("el-button"),N=m("el-dialog");return d(),v("div",null,[o(N,{title:"订单详情",fullscreen:"",modelValue:e.isShowDialog,"onUpdate:modelValue":u[0]||(u[0]=_=>e.isShowDialog=_)},{footer:t(()=>[s("span",he,[e.ruleForm.payStatus!=1?(d(),b(h,{key:0,type:"success",size:"default",onClick:I},{default:t(()=>[p("支 付")]),_:1})):F("",!0),o(h,{onClick:D,size:"default"},{default:t(()=>[p("取 消")]),_:1})])]),default:t(()=>[s("div",H,[o(n,null,{default:t(()=>[o(l,{xs:24,sm:24},{default:t(()=>[o(x,{shadow:"hover"},{default:t(()=>[s("div",Q,[s("div",$,[o(n,null,{default:t(()=>[o(l,{span:24,class:"personal-title mb18"},{default:t(()=>[p(a(e.ruleForm.orderTitle),1)]),_:1}),o(l,{span:24},{default:t(()=>[o(n,null,{default:t(()=>[o(l,{xs:24,sm:8,class:"personal-item mb6"},{default:t(()=>[X,s("div",j,a(e.ruleForm.orderSn),1)]),_:1}),o(l,{xs:24,sm:4,class:"personal-item mb6"},{default:t(()=>[G,s("div",J,a(e.ruleForm.statusStr),1)]),_:1}),o(l,{xs:24,sm:4,class:"personal-item mb6"},{default:t(()=>[K,s("div",W,a(e.ruleForm.tradeStatusStr),1)]),_:1}),o(l,{xs:24,sm:4,class:"personal-item mb6"},{default:t(()=>[Y,s("div",Z,a(e.ruleForm.payStatusStr),1)]),_:1})]),_:1})]),_:1}),o(l,{span:24},{default:t(()=>[o(n,null,{default:t(()=>[o(l,{xs:24,sm:8,class:"personal-item mb6"},{default:t(()=>[ee,s("div",te,a(e.ruleForm.orderAmount),1)]),_:1}),o(l,{xs:24,sm:4,class:"personal-item mb6"},{default:t(()=>[se,s("div",oe,a(e.ruleForm.nickName),1)]),_:1}),o(l,{xs:24,sm:4,class:"personal-item mb6"},{default:t(()=>[le,s("div",ae,a(e.ruleForm.created),1)]),_:1})]),_:1})]),_:1}),e.ruleForm.buyerMsg!=null&&e.ruleForm.buyerMsg!=""?(d(),b(l,{key:0,span:24},{default:t(()=>[o(n,null,{default:t(()=>[o(l,{xs:24,sm:24,class:"personal-item mb6"},{default:t(()=>[re,s("div",ie,a(e.ruleForm.buyerMsg),1)]),_:1})]),_:1})]),_:1})):F("",!0),e.ruleForm.remarks!=null&&e.ruleForm.remarks!=""?(d(),b(l,{key:1,span:24},{default:t(()=>[o(n,null,{default:t(()=>[o(l,{xs:24,sm:24,class:"personal-item mb6"},{default:t(()=>[ne,s("div",de,a(e.ruleForm.remarks),1)]),_:1})]),_:1})]),_:1})):F("",!0)]),_:1})])])]),_:1})]),_:1}),o(l,{span:24},{default:t(()=>[o(x,{shadow:"hover",class:"mt15 personal-edit",header:"商品信息"},{default:t(()=>[(d(!0),v(B,null,R(e.ruleForm.orderItemList,(_,V)=>(d(),v("div",{class:"personal-edit-safe-box",key:V},[s("div",me,[s("div",ce,[s("div",ue,a(_.productTitle),1)]),s("div",_e,[o(h,{type:"text"},{default:t(()=>[p("数量 X "+a(_.productCount),1)]),_:2},1024)])])]))),128))]),_:1})]),_:1})]),_:1})]),s("div",pe,[s("div",fe,null,512)])]),_:1},8,["modelValue"])])}}});const st=E(be,[["__scopeId","data-v-a90dd9f5"]]);export{st as default};
