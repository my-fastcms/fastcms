import{s as t}from"./index.56ec5e99.js";function s(e){return t({url:"/admin/user/list",method:"get",params:e})}function n(e){return t({url:"/admin/user/save",method:"post",data:e,headers:{"Content-Type":"application/x-www-form-urlencoded"}})}function a(e){return t({url:"/admin/user/delete/"+e,method:"post"})}function o(e){return t({url:"/admin/user/"+e+"/get",method:"get"})}function u(){return t({url:"/admin/user/tag/list",method:"get"})}function d(e){return t({url:"/admin/user/password/update",method:"post",data:e,headers:{"Content-Type":"application/x-www-form-urlencoded"}})}export{u as a,o as b,a as d,s as g,n as s,d as u};