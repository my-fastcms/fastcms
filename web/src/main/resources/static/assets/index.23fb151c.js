var r=Object.defineProperty;var t=Object.getOwnPropertySymbols;var d=Object.prototype.hasOwnProperty,p=Object.prototype.propertyIsEnumerable;var u=(o,e,n)=>e in o?r(o,e,{enumerable:!0,configurable:!0,writable:!0,value:n}):o[e]=n,a=(o,e)=>{for(var n in e||(e={}))d.call(e,n)&&u(o,n,e[n]);if(t)for(var n of t(e))p.call(e,n)&&u(o,n,e[n]);return o};import{_ as m}from"./index.fabd0e58.js";import{j as c,t as i,l as f,m as v,z as B,D as _}from"./vendor.09ddcd37.js";const F={name:"menu2",setup(){const o=c({val:""});return a({},i(o))}};function D(o,e,n,V,$,j){const l=f("el-input");return _(),v("div",null,[B(l,{modelValue:o.val,"onUpdate:modelValue":e[0]||(e[0]=s=>o.val=s),placeholder:"menu2\uFF1A\u8BF7\u8F93\u5165\u5185\u5BB9\u6D4B\u8BD5\u8DEF\u7531\u7F13\u5B58"},null,8,["modelValue"])])}var b=m(F,[["render",D]]);export{b as default};
