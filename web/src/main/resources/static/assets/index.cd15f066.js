import{s as e}from"./index.9d91ddd7.js";function d(t){return e({url:"/admin/order/list",method:"get",params:t})}function o(t){return e({url:"/admin/order/detail/"+t,method:"get"})}function a(t){return e({url:"/admin/order/delete/"+t,method:"post"})}function u(t){return e({url:"/admin/order/payment/list",method:"get",params:t})}function i(t){return e({url:"/admin/order/payment/detail/"+t,method:"get"})}function s(t){return e({url:"/admin/order/cashout/list",method:"get",params:t})}function m(t){return e({url:"/admin/order/cashout/detail/"+t,method:"get"})}function l(t){return e({url:"/admin/order/cashout/confirm/"+t,method:"post"})}function c(t,r){return e({url:"/admin/order/cashout/refuse/"+t,method:"post",data:r,headers:{"Content-Type":"application/x-www-form-urlencoded"}})}export{m as a,d as b,l as c,a as d,o as e,u as f,s as g,i as h,c as r};
