import{s as r}from"./index-0ad00899.js";function s(){return{getUserList(e){return r({url:"/admin/user/list",method:"get",params:e})},saveUser(e){return r({url:"/admin/user/save",method:"post",data:e,headers:{"Content-Type":"application/x-www-form-urlencoded"}})},delUser(e){return r({url:"/admin/user/delete/"+e,method:"post"})},getUserInfo(e){return r({url:"/admin/user/"+e+"/get",method:"get"})},getTagList(){return r({url:"/admin/user/tag/list",method:"get"})},updatePassword(e){return r({url:"/admin/user/password/update",method:"post",data:e,headers:{"Content-Type":"application/x-www-form-urlencoded"}})},resetPassword(e){return r({url:"/admin/user/reset/password?userId="+e,method:"post"})},changeUserType(e){return r({url:"/admin/user/changUserType",method:"post",data:e,headers:{"Content-Type":"application/x-www-form-urlencoded"}})}}}export{s as U};
