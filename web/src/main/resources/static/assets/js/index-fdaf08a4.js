import{s as r}from"./index-0ad00899.js";function o(){return{getOrderList(e){return r({url:"/admin/order/list",method:"get",params:e})},getOrderDetail(e){return r({url:"/admin/order/detail/"+e,method:"get"})},delOrder(e){return r({url:"/admin/order/delete/"+e,method:"post"})},getPaymentList(e){return r({url:"/admin/order/payment/list",method:"get",params:e})},getPaymentDetail(e){return r({url:"/admin/order/payment/detail/"+e,method:"get"})},getCashoutList(e){return r({url:"/admin/order/cashout/list",method:"get",params:e})},getCashoutDetail(e){return r({url:"/admin/order/cashout/detail/"+e,method:"get"})},confirmCashOut(e){return r({url:"/admin/order/cashout/confirm/"+e,method:"post"})},refuseCashOut(e,t){return r({url:"/admin/order/cashout/refuse/"+e,method:"post",data:t,headers:{"Content-Type":"application/x-www-form-urlencoded"}})}}}export{o as O};
