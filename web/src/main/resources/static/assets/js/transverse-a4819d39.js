import{_ as r,u as y}from"./index-0ad00899.js";import{u as T}from"./vue-router-c1461dfc.js";import{s as h}from"./pinia-b6f74250.js";import{H as s,aP as a,e as v,i as R,f as i,n as p,ah as C,o as M,O as g,P as k,V as m,u as n}from"./@vue-6dabbe94.js";import"./vue-i18n-b3581196.js";import"./@intlify-ea47d1db.js";import"./source-map-7d7e1c08.js";import"./vue-b1ba6f14.js";import"./js-cookie-edb2da2a.js";import"./@element-plus-64e6e14a.js";import"./nprogress-08a53ce8.js";import"./axios-4a70c6fc.js";import"./qs-c5b6dbf2.js";import"./side-channel-394f276c.js";import"./get-intrinsic-bd2830fd.js";import"./has-symbols-e8f3ca0e.js";import"./has-proto-f7d0b240.js";import"./function-bind-22e7ee79.js";import"./has-26d28e02.js";import"./call-bind-e5c1c8b0.js";import"./object-inspect-8fd4bade.js";import"./element-plus-91d6d961.js";import"./lodash-es-9851428c.js";import"./@vueuse-e129c873.js";import"./@popperjs-c75af06c.js";import"./@ctrl-f8748455.js";import"./dayjs-f6fdf7b4.js";import"./async-validator-dee29e8b.js";import"./memoize-one-297ddbcb.js";import"./escape-html-b8998962.js";import"./normalize-wheel-es-ed76fb12.js";import"./@floating-ui-880a26aa.js";import"./mitt-f7ef348c.js";import"./vue-grid-layout-ec2a62c3.js";const x=s({name:"layoutTransverse"}),so=s({...x,setup(P){const c=a(()=>r(()=>import("./header-2cb0ee07.js"),["assets/js/header-2cb0ee07.js","assets/js/index-0ad00899.js","assets/js/@vue-6dabbe94.js","assets/js/pinia-b6f74250.js","assets/js/vue-router-c1461dfc.js","assets/js/vue-i18n-b3581196.js","assets/js/@intlify-ea47d1db.js","assets/js/source-map-7d7e1c08.js","assets/js/vue-b1ba6f14.js","assets/js/js-cookie-edb2da2a.js","assets/js/@element-plus-64e6e14a.js","assets/js/nprogress-08a53ce8.js","assets/css/nprogress-8b89e2e0.css","assets/js/axios-4a70c6fc.js","assets/js/qs-c5b6dbf2.js","assets/js/side-channel-394f276c.js","assets/js/get-intrinsic-bd2830fd.js","assets/js/has-symbols-e8f3ca0e.js","assets/js/has-proto-f7d0b240.js","assets/js/function-bind-22e7ee79.js","assets/js/has-26d28e02.js","assets/js/call-bind-e5c1c8b0.js","assets/js/object-inspect-8fd4bade.js","assets/js/element-plus-91d6d961.js","assets/js/lodash-es-9851428c.js","assets/js/@vueuse-e129c873.js","assets/js/@popperjs-c75af06c.js","assets/js/@ctrl-f8748455.js","assets/js/dayjs-f6fdf7b4.js","assets/js/async-validator-dee29e8b.js","assets/js/memoize-one-297ddbcb.js","assets/js/escape-html-b8998962.js","assets/js/normalize-wheel-es-ed76fb12.js","assets/js/@floating-ui-880a26aa.js","assets/js/mitt-f7ef348c.js","assets/js/vue-grid-layout-ec2a62c3.js","assets/css/fastcms-01cf5f8f.css"])),u=a(()=>r(()=>import("./main-19db8421.js"),["assets/js/main-19db8421.js","assets/js/index-0ad00899.js","assets/js/@vue-6dabbe94.js","assets/js/pinia-b6f74250.js","assets/js/vue-router-c1461dfc.js","assets/js/vue-i18n-b3581196.js","assets/js/@intlify-ea47d1db.js","assets/js/source-map-7d7e1c08.js","assets/js/vue-b1ba6f14.js","assets/js/js-cookie-edb2da2a.js","assets/js/@element-plus-64e6e14a.js","assets/js/nprogress-08a53ce8.js","assets/css/nprogress-8b89e2e0.css","assets/js/axios-4a70c6fc.js","assets/js/qs-c5b6dbf2.js","assets/js/side-channel-394f276c.js","assets/js/get-intrinsic-bd2830fd.js","assets/js/has-symbols-e8f3ca0e.js","assets/js/has-proto-f7d0b240.js","assets/js/function-bind-22e7ee79.js","assets/js/has-26d28e02.js","assets/js/call-bind-e5c1c8b0.js","assets/js/object-inspect-8fd4bade.js","assets/js/element-plus-91d6d961.js","assets/js/lodash-es-9851428c.js","assets/js/@vueuse-e129c873.js","assets/js/@popperjs-c75af06c.js","assets/js/@ctrl-f8748455.js","assets/js/dayjs-f6fdf7b4.js","assets/js/async-validator-dee29e8b.js","assets/js/memoize-one-297ddbcb.js","assets/js/escape-html-b8998962.js","assets/js/normalize-wheel-es-ed76fb12.js","assets/js/@floating-ui-880a26aa.js","assets/js/mitt-f7ef348c.js","assets/js/vue-grid-layout-ec2a62c3.js","assets/css/fastcms-01cf5f8f.css"])),o=v(),l=y(),{themeConfig:_}=h(l),f=T(),t=()=>{o.value.layoutMainScrollbarRef.update()},e=()=>{p(()=>{setTimeout(()=>{t(),o.value.layoutMainScrollbarRef.wrapRef.scrollTop=0},500)})};return R(()=>{e()}),i(()=>f.path,()=>{e()}),i(()=>_.value.isTagsview,()=>{p(()=>{t()})},{deep:!0}),(b,w)=>{const d=C("el-container");return M(),g(d,{class:"layout-container flex-center layout-backtop"},{default:k(()=>[m(n(c)),m(n(u),{ref_key:"layoutMainRef",ref:o},null,512)]),_:1})}}});export{so as default};
