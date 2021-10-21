var w=Object.defineProperty;var c=Object.getOwnPropertySymbols;var k=Object.prototype.hasOwnProperty,S=Object.prototype.propertyIsEnumerable;var f=(e,i,n)=>i in e?w(e,i,{enumerable:!0,configurable:!0,writable:!0,value:n}):e[i]=n,u=(e,i)=>{for(var n in i||(i={}))k.call(i,n)&&f(e,n,i[n]);if(c)for(var n of c(i))S.call(i,n)&&f(e,n,i[n]);return e};import{i as x,r as m,j as W,o as T,t as $,n as B,w as I,v as O,D as a,m as r,p,q as h,L as y,y as R,a6 as A,K as g}from"./vendor.09ddcd37.js";import{_ as N}from"./index.fabd0e58.js";const _=x({name:"noticeBar",props:{mode:{type:String,default:()=>""},text:{type:String,default:()=>""},color:{type:String,default:()=>"var(--color-warning)"},background:{type:String,default:()=>"var(--color-warning-light-9)"},size:{type:[Number,String],default:()=>14},height:{type:[Number,String],default:()=>40},delay:{type:[Number,String],default:()=>1},speed:{type:[Number,String],default:()=>100},scrollable:{type:Boolean,default:()=>!1},leftIcon:{type:String,default:()=>""},rightIcon:{type:String,default:()=>""}},setup(e,{emit:i}){const n=m(),o=m(),t=W({order:1,oneTime:"",twoTime:"",warpOWidth:"",textOWidth:"",isMode:!1}),l=()=>{B(()=>{t.warpOWidth=n.value.offsetWidth,t.textOWidth=o.value.offsetWidth,document.styleSheets[0].insertRule(`@keyframes oneAnimation {0% {left: 0px;} 100% {left: -${t.textOWidth}px;}}`),document.styleSheets[0].insertRule(`@keyframes twoAnimation {0% {left: ${t.warpOWidth}px;} 100% {left: -${t.textOWidth}px;}}`),s(),setTimeout(()=>{d()},e.delay*1e3)})},s=()=>{t.oneTime=t.textOWidth/e.speed,t.twoTime=(t.textOWidth+t.warpOWidth)/e.speed},d=()=>{t.order===1?(o.value.style.cssText=`animation: oneAnimation ${t.oneTime}s linear; opactity: 1;}`,t.order=2):o.value.style.cssText=`animation: twoAnimation ${t.twoTime}s linear infinite; opacity: 1;`},v=()=>{o.value.addEventListener("animationend",()=>{d()},!1)},b=()=>{if(!e.mode)return!1;e.mode==="closeable"?(t.isMode=!0,i("close")):e.mode==="link"&&i("link")};return T(()=>{if(e.scrollable)return!1;l(),v()}),u({noticeBarWarpRef:n,noticeBarTextRef:o,onRightIconClick:b},$(t))}}),C={class:"notice-bar-warp-text-box",ref:"noticeBarWarpRef"},z={key:1,class:"notice-bar-warp-slot"};function M(e,i,n,o,t,l){return I((a(),r("div",{class:"notice-bar",style:g({background:e.background,height:`${e.height}px`})},[p("div",{class:"notice-bar-warp",style:g({color:e.color,fontSize:`${e.size}px`})},[e.leftIcon?(a(),r("i",{key:0,class:h(["notice-bar-warp-left-icon",e.leftIcon])},null,2)):y("",!0),p("div",C,[e.scrollable?(a(),r("div",z,[A(e.$slots,"default",{},void 0,!0)])):(a(),r("div",{key:0,class:"notice-bar-warp-text",ref:"noticeBarTextRef"},R(e.text),513))],512),e.rightIcon?(a(),r("i",{key:1,class:h(["notice-bar-warp-right-icon",e.rightIcon]),onClick:i[0]||(i[0]=(...s)=>e.onRightIconClick&&e.onRightIconClick(...s))},null,2)):y("",!0)],4)],4)),[[O,!e.isMode]])}var L=N(_,[["render",M],["__scopeId","data-v-4a97233c"]]);export{L as N};
