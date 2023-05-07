var at=Object.defineProperty;var W=Object.getOwnPropertySymbols;var rt=Object.prototype.hasOwnProperty,st=Object.prototype.propertyIsEnumerable;var $=(h,T,A)=>T in h?at(h,T,{enumerable:!0,configurable:!0,writable:!0,value:A}):h[T]=A,z=(h,T)=>{for(var A in T||(T={}))rt.call(T,A)&&$(h,A,T[A]);if(W)for(var A of W(T))st.call(T,A)&&$(h,A,T[A]);return h};import{a as nt,p as it,c as ot}from"./client.dddc56ad.js";import{a7 as lt,j as ut,a8 as ht,t as ft,l as O,m as Q,z as _,A as g,D as N,p as m,Q as G,L as K,x as H,y as P,Y as dt,X as gt,G as ct,B as mt,C as vt}from"./vendor.07d41a1e.js";import{_ as pt}from"./index.b56fc134.js";var Z={exports:{}};(function(h,T){var A;(function(D,B){h.exports=B()})(lt,function(){function D(t){this.mode=C.MODE_8BIT_BYTE,this.data=t,this.parsedData=[];for(var e=0,a=this.data.length;e<a;e++){var r=[],s=this.data.charCodeAt(e);s>65536?(r[0]=240|(s&1835008)>>>18,r[1]=128|(s&258048)>>>12,r[2]=128|(s&4032)>>>6,r[3]=128|s&63):s>2048?(r[0]=224|(s&61440)>>>12,r[1]=128|(s&4032)>>>6,r[2]=128|s&63):s>128?(r[0]=192|(s&1984)>>>6,r[1]=128|s&63):r[0]=s,this.parsedData.push(r)}this.parsedData=Array.prototype.concat.apply([],this.parsedData),this.parsedData.length!=this.data.length&&(this.parsedData.unshift(191),this.parsedData.unshift(187),this.parsedData.unshift(239))}D.prototype={getLength:function(t){return this.parsedData.length},write:function(t){for(var e=0,a=this.parsedData.length;e<a;e++)t.put(this.parsedData[e],8)}};function B(t,e){this.typeNumber=t,this.errorCorrectLevel=e,this.modules=null,this.moduleCount=0,this.dataCache=null,this.dataList=[]}B.prototype={addData:function(t){var e=new D(t);this.dataList.push(e),this.dataCache=null},isDark:function(t,e){if(t<0||this.moduleCount<=t||e<0||this.moduleCount<=e)throw new Error(t+","+e);return this.modules[t][e]},getModuleCount:function(){return this.moduleCount},make:function(){this.makeImpl(!1,this.getBestMaskPattern())},makeImpl:function(t,e){this.moduleCount=this.typeNumber*4+17,this.modules=new Array(this.moduleCount);for(var a=0;a<this.moduleCount;a++){this.modules[a]=new Array(this.moduleCount);for(var r=0;r<this.moduleCount;r++)this.modules[a][r]=null}this.setupPositionProbePattern(0,0),this.setupPositionProbePattern(this.moduleCount-7,0),this.setupPositionProbePattern(0,this.moduleCount-7),this.setupPositionAdjustPattern(),this.setupTimingPattern(),this.setupTypeInfo(t,e),this.typeNumber>=7&&this.setupTypeNumber(t),this.dataCache==null&&(this.dataCache=B.createData(this.typeNumber,this.errorCorrectLevel,this.dataList)),this.mapData(this.dataCache,e)},setupPositionProbePattern:function(t,e){for(var a=-1;a<=7;a++)if(!(t+a<=-1||this.moduleCount<=t+a))for(var r=-1;r<=7;r++)e+r<=-1||this.moduleCount<=e+r||(0<=a&&a<=6&&(r==0||r==6)||0<=r&&r<=6&&(a==0||a==6)||2<=a&&a<=4&&2<=r&&r<=4?this.modules[t+a][e+r]=!0:this.modules[t+a][e+r]=!1)},getBestMaskPattern:function(){for(var t=0,e=0,a=0;a<8;a++){this.makeImpl(!0,a);var r=c.getLostPoint(this);(a==0||t>r)&&(t=r,e=a)}return e},createMovieClip:function(t,e,a){var r=t.createEmptyMovieClip(e,a),s=1;this.make();for(var i=0;i<this.modules.length;i++)for(var o=i*s,n=0;n<this.modules[i].length;n++){var l=n*s,f=this.modules[i][n];f&&(r.beginFill(0,100),r.moveTo(l,o),r.lineTo(l+s,o),r.lineTo(l+s,o+s),r.lineTo(l,o+s),r.endFill())}return r},setupTimingPattern:function(){for(var t=8;t<this.moduleCount-8;t++)this.modules[t][6]==null&&(this.modules[t][6]=t%2==0);for(var e=8;e<this.moduleCount-8;e++)this.modules[6][e]==null&&(this.modules[6][e]=e%2==0)},setupPositionAdjustPattern:function(){for(var t=c.getPatternPosition(this.typeNumber),e=0;e<t.length;e++)for(var a=0;a<t.length;a++){var r=t[e],s=t[a];if(this.modules[r][s]==null)for(var i=-2;i<=2;i++)for(var o=-2;o<=2;o++)i==-2||i==2||o==-2||o==2||i==0&&o==0?this.modules[r+i][s+o]=!0:this.modules[r+i][s+o]=!1}},setupTypeNumber:function(t){for(var e=c.getBCHTypeNumber(this.typeNumber),a=0;a<18;a++){var r=!t&&(e>>a&1)==1;this.modules[Math.floor(a/3)][a%3+this.moduleCount-8-3]=r}for(var a=0;a<18;a++){var r=!t&&(e>>a&1)==1;this.modules[a%3+this.moduleCount-8-3][Math.floor(a/3)]=r}},setupTypeInfo:function(t,e){for(var a=this.errorCorrectLevel<<3|e,r=c.getBCHTypeInfo(a),s=0;s<15;s++){var i=!t&&(r>>s&1)==1;s<6?this.modules[s][8]=i:s<8?this.modules[s+1][8]=i:this.modules[this.moduleCount-15+s][8]=i}for(var s=0;s<15;s++){var i=!t&&(r>>s&1)==1;s<8?this.modules[8][this.moduleCount-s-1]=i:s<9?this.modules[8][15-s-1+1]=i:this.modules[8][15-s-1]=i}this.modules[this.moduleCount-8][8]=!t},mapData:function(t,e){for(var a=-1,r=this.moduleCount-1,s=7,i=0,o=this.moduleCount-1;o>0;o-=2)for(o==6&&o--;;){for(var n=0;n<2;n++)if(this.modules[r][o-n]==null){var l=!1;i<t.length&&(l=(t[i]>>>s&1)==1);var f=c.getMask(e,r,o-n);f&&(l=!l),this.modules[r][o-n]=l,s--,s==-1&&(i++,s=7)}if(r+=a,r<0||this.moduleCount<=r){r-=a,a=-a;break}}}},B.PAD0=236,B.PAD1=17,B.createData=function(t,e,a){for(var r=L.getRSBlocks(t,e),s=new X,i=0;i<a.length;i++){var o=a[i];s.put(o.mode,4),s.put(o.getLength(),c.getLengthInBits(o.mode,t)),o.write(s)}for(var n=0,i=0;i<r.length;i++)n+=r[i].dataCount;if(s.getLengthInBits()>n*8)throw new Error("code length overflow. ("+s.getLengthInBits()+">"+n*8+")");for(s.getLengthInBits()+4<=n*8&&s.put(0,4);s.getLengthInBits()%8!=0;)s.putBit(!1);for(;!(s.getLengthInBits()>=n*8||(s.put(B.PAD0,8),s.getLengthInBits()>=n*8));)s.put(B.PAD1,8);return B.createBytes(s,r)},B.createBytes=function(t,e){for(var a=0,r=0,s=0,i=new Array(e.length),o=new Array(e.length),n=0;n<e.length;n++){var l=e[n].dataCount,f=e[n].totalCount-l;r=Math.max(r,l),s=Math.max(s,f),i[n]=new Array(l);for(var u=0;u<i[n].length;u++)i[n][u]=255&t.buffer[u+a];a+=l;var w=c.getErrorCorrectPolynomial(f),k=new b(i[n],w.getLength()-1),F=k.mod(w);o[n]=new Array(w.getLength()-1);for(var u=0;u<o[n].length;u++){var y=u+F.getLength()-o[n].length;o[n][u]=y>=0?F.get(y):0}}for(var M=0,u=0;u<e.length;u++)M+=e[u].totalCount;for(var x=new Array(M),S=0,u=0;u<r;u++)for(var n=0;n<e.length;n++)u<i[n].length&&(x[S++]=i[n][u]);for(var u=0;u<s;u++)for(var n=0;n<e.length;n++)u<o[n].length&&(x[S++]=o[n][u]);return x};for(var C={MODE_NUMBER:1<<0,MODE_ALPHA_NUM:1<<1,MODE_8BIT_BYTE:1<<2,MODE_KANJI:1<<3},d={L:1,M:0,Q:3,H:2},E={PATTERN000:0,PATTERN001:1,PATTERN010:2,PATTERN011:3,PATTERN100:4,PATTERN101:5,PATTERN110:6,PATTERN111:7},c={PATTERN_POSITION_TABLE:[[],[6,18],[6,22],[6,26],[6,30],[6,34],[6,22,38],[6,24,42],[6,26,46],[6,28,50],[6,30,54],[6,32,58],[6,34,62],[6,26,46,66],[6,26,48,70],[6,26,50,74],[6,30,54,78],[6,30,56,82],[6,30,58,86],[6,34,62,90],[6,28,50,72,94],[6,26,50,74,98],[6,30,54,78,102],[6,28,54,80,106],[6,32,58,84,110],[6,30,58,86,114],[6,34,62,90,118],[6,26,50,74,98,122],[6,30,54,78,102,126],[6,26,52,78,104,130],[6,30,56,82,108,134],[6,34,60,86,112,138],[6,30,58,86,114,142],[6,34,62,90,118,146],[6,30,54,78,102,126,150],[6,24,50,76,102,128,154],[6,28,54,80,106,132,158],[6,32,58,84,110,136,162],[6,26,54,82,110,138,166],[6,30,58,86,114,142,170]],G15:1<<10|1<<8|1<<5|1<<4|1<<2|1<<1|1<<0,G18:1<<12|1<<11|1<<10|1<<9|1<<8|1<<5|1<<2|1<<0,G15_MASK:1<<14|1<<12|1<<10|1<<4|1<<1,getBCHTypeInfo:function(t){for(var e=t<<10;c.getBCHDigit(e)-c.getBCHDigit(c.G15)>=0;)e^=c.G15<<c.getBCHDigit(e)-c.getBCHDigit(c.G15);return(t<<10|e)^c.G15_MASK},getBCHTypeNumber:function(t){for(var e=t<<12;c.getBCHDigit(e)-c.getBCHDigit(c.G18)>=0;)e^=c.G18<<c.getBCHDigit(e)-c.getBCHDigit(c.G18);return t<<12|e},getBCHDigit:function(t){for(var e=0;t!=0;)e++,t>>>=1;return e},getPatternPosition:function(t){return c.PATTERN_POSITION_TABLE[t-1]},getMask:function(t,e,a){switch(t){case E.PATTERN000:return(e+a)%2==0;case E.PATTERN001:return e%2==0;case E.PATTERN010:return a%3==0;case E.PATTERN011:return(e+a)%3==0;case E.PATTERN100:return(Math.floor(e/2)+Math.floor(a/3))%2==0;case E.PATTERN101:return e*a%2+e*a%3==0;case E.PATTERN110:return(e*a%2+e*a%3)%2==0;case E.PATTERN111:return(e*a%3+(e+a)%2)%2==0;default:throw new Error("bad maskPattern:"+t)}},getErrorCorrectPolynomial:function(t){for(var e=new b([1],0),a=0;a<t;a++)e=e.multiply(new b([1,v.gexp(a)],0));return e},getLengthInBits:function(t,e){if(1<=e&&e<10)switch(t){case C.MODE_NUMBER:return 10;case C.MODE_ALPHA_NUM:return 9;case C.MODE_8BIT_BYTE:return 8;case C.MODE_KANJI:return 8;default:throw new Error("mode:"+t)}else if(e<27)switch(t){case C.MODE_NUMBER:return 12;case C.MODE_ALPHA_NUM:return 11;case C.MODE_8BIT_BYTE:return 16;case C.MODE_KANJI:return 10;default:throw new Error("mode:"+t)}else if(e<41)switch(t){case C.MODE_NUMBER:return 14;case C.MODE_ALPHA_NUM:return 13;case C.MODE_8BIT_BYTE:return 16;case C.MODE_KANJI:return 12;default:throw new Error("mode:"+t)}else throw new Error("type:"+e)},getLostPoint:function(t){for(var e=t.getModuleCount(),a=0,r=0;r<e;r++)for(var s=0;s<e;s++){for(var i=0,o=t.isDark(r,s),n=-1;n<=1;n++)if(!(r+n<0||e<=r+n))for(var l=-1;l<=1;l++)s+l<0||e<=s+l||n==0&&l==0||o==t.isDark(r+n,s+l)&&i++;i>5&&(a+=3+i-5)}for(var r=0;r<e-1;r++)for(var s=0;s<e-1;s++){var f=0;t.isDark(r,s)&&f++,t.isDark(r+1,s)&&f++,t.isDark(r,s+1)&&f++,t.isDark(r+1,s+1)&&f++,(f==0||f==4)&&(a+=3)}for(var r=0;r<e;r++)for(var s=0;s<e-6;s++)t.isDark(r,s)&&!t.isDark(r,s+1)&&t.isDark(r,s+2)&&t.isDark(r,s+3)&&t.isDark(r,s+4)&&!t.isDark(r,s+5)&&t.isDark(r,s+6)&&(a+=40);for(var s=0;s<e;s++)for(var r=0;r<e-6;r++)t.isDark(r,s)&&!t.isDark(r+1,s)&&t.isDark(r+2,s)&&t.isDark(r+3,s)&&t.isDark(r+4,s)&&!t.isDark(r+5,s)&&t.isDark(r+6,s)&&(a+=40);for(var u=0,s=0;s<e;s++)for(var r=0;r<e;r++)t.isDark(r,s)&&u++;var w=Math.abs(100*u/e/e-50)/5;return a+=w*10,a}},v={glog:function(t){if(t<1)throw new Error("glog("+t+")");return v.LOG_TABLE[t]},gexp:function(t){for(;t<0;)t+=255;for(;t>=256;)t-=255;return v.EXP_TABLE[t]},EXP_TABLE:new Array(256),LOG_TABLE:new Array(256)},p=0;p<8;p++)v.EXP_TABLE[p]=1<<p;for(var p=8;p<256;p++)v.EXP_TABLE[p]=v.EXP_TABLE[p-4]^v.EXP_TABLE[p-5]^v.EXP_TABLE[p-6]^v.EXP_TABLE[p-8];for(var p=0;p<255;p++)v.LOG_TABLE[v.EXP_TABLE[p]]=p;function b(t,e){if(t.length==null)throw new Error(t.length+"/"+e);for(var a=0;a<t.length&&t[a]==0;)a++;this.num=new Array(t.length-a+e);for(var r=0;r<t.length-a;r++)this.num[r]=t[r+a]}b.prototype={get:function(t){return this.num[t]},getLength:function(){return this.num.length},multiply:function(t){for(var e=new Array(this.getLength()+t.getLength()-1),a=0;a<this.getLength();a++)for(var r=0;r<t.getLength();r++)e[a+r]^=v.gexp(v.glog(this.get(a))+v.glog(t.get(r)));return new b(e,0)},mod:function(t){if(this.getLength()-t.getLength()<0)return this;for(var e=v.glog(this.get(0))-v.glog(t.get(0)),a=new Array(this.getLength()),r=0;r<this.getLength();r++)a[r]=this.get(r);for(var r=0;r<t.getLength();r++)a[r]^=v.gexp(v.glog(t.get(r))+e);return new b(a,0).mod(t)}};function L(t,e){this.totalCount=t,this.dataCount=e}L.RS_BLOCK_TABLE=[[1,26,19],[1,26,16],[1,26,13],[1,26,9],[1,44,34],[1,44,28],[1,44,22],[1,44,16],[1,70,55],[1,70,44],[2,35,17],[2,35,13],[1,100,80],[2,50,32],[2,50,24],[4,25,9],[1,134,108],[2,67,43],[2,33,15,2,34,16],[2,33,11,2,34,12],[2,86,68],[4,43,27],[4,43,19],[4,43,15],[2,98,78],[4,49,31],[2,32,14,4,33,15],[4,39,13,1,40,14],[2,121,97],[2,60,38,2,61,39],[4,40,18,2,41,19],[4,40,14,2,41,15],[2,146,116],[3,58,36,2,59,37],[4,36,16,4,37,17],[4,36,12,4,37,13],[2,86,68,2,87,69],[4,69,43,1,70,44],[6,43,19,2,44,20],[6,43,15,2,44,16],[4,101,81],[1,80,50,4,81,51],[4,50,22,4,51,23],[3,36,12,8,37,13],[2,116,92,2,117,93],[6,58,36,2,59,37],[4,46,20,6,47,21],[7,42,14,4,43,15],[4,133,107],[8,59,37,1,60,38],[8,44,20,4,45,21],[12,33,11,4,34,12],[3,145,115,1,146,116],[4,64,40,5,65,41],[11,36,16,5,37,17],[11,36,12,5,37,13],[5,109,87,1,110,88],[5,65,41,5,66,42],[5,54,24,7,55,25],[11,36,12],[5,122,98,1,123,99],[7,73,45,3,74,46],[15,43,19,2,44,20],[3,45,15,13,46,16],[1,135,107,5,136,108],[10,74,46,1,75,47],[1,50,22,15,51,23],[2,42,14,17,43,15],[5,150,120,1,151,121],[9,69,43,4,70,44],[17,50,22,1,51,23],[2,42,14,19,43,15],[3,141,113,4,142,114],[3,70,44,11,71,45],[17,47,21,4,48,22],[9,39,13,16,40,14],[3,135,107,5,136,108],[3,67,41,13,68,42],[15,54,24,5,55,25],[15,43,15,10,44,16],[4,144,116,4,145,117],[17,68,42],[17,50,22,6,51,23],[19,46,16,6,47,17],[2,139,111,7,140,112],[17,74,46],[7,54,24,16,55,25],[34,37,13],[4,151,121,5,152,122],[4,75,47,14,76,48],[11,54,24,14,55,25],[16,45,15,14,46,16],[6,147,117,4,148,118],[6,73,45,14,74,46],[11,54,24,16,55,25],[30,46,16,2,47,17],[8,132,106,4,133,107],[8,75,47,13,76,48],[7,54,24,22,55,25],[22,45,15,13,46,16],[10,142,114,2,143,115],[19,74,46,4,75,47],[28,50,22,6,51,23],[33,46,16,4,47,17],[8,152,122,4,153,123],[22,73,45,3,74,46],[8,53,23,26,54,24],[12,45,15,28,46,16],[3,147,117,10,148,118],[3,73,45,23,74,46],[4,54,24,31,55,25],[11,45,15,31,46,16],[7,146,116,7,147,117],[21,73,45,7,74,46],[1,53,23,37,54,24],[19,45,15,26,46,16],[5,145,115,10,146,116],[19,75,47,10,76,48],[15,54,24,25,55,25],[23,45,15,25,46,16],[13,145,115,3,146,116],[2,74,46,29,75,47],[42,54,24,1,55,25],[23,45,15,28,46,16],[17,145,115],[10,74,46,23,75,47],[10,54,24,35,55,25],[19,45,15,35,46,16],[17,145,115,1,146,116],[14,74,46,21,75,47],[29,54,24,19,55,25],[11,45,15,46,46,16],[13,145,115,6,146,116],[14,74,46,23,75,47],[44,54,24,7,55,25],[59,46,16,1,47,17],[12,151,121,7,152,122],[12,75,47,26,76,48],[39,54,24,14,55,25],[22,45,15,41,46,16],[6,151,121,14,152,122],[6,75,47,34,76,48],[46,54,24,10,55,25],[2,45,15,64,46,16],[17,152,122,4,153,123],[29,74,46,14,75,47],[49,54,24,10,55,25],[24,45,15,46,46,16],[4,152,122,18,153,123],[13,74,46,32,75,47],[48,54,24,14,55,25],[42,45,15,32,46,16],[20,147,117,4,148,118],[40,75,47,7,76,48],[43,54,24,22,55,25],[10,45,15,67,46,16],[19,148,118,6,149,119],[18,75,47,31,76,48],[34,54,24,34,55,25],[20,45,15,61,46,16]],L.getRSBlocks=function(t,e){var a=L.getRsBlockTable(t,e);if(a==null)throw new Error("bad rs block @ typeNumber:"+t+"/errorCorrectLevel:"+e);for(var r=a.length/3,s=[],i=0;i<r;i++)for(var o=a[i*3+0],n=a[i*3+1],l=a[i*3+2],f=0;f<o;f++)s.push(new L(n,l));return s},L.getRsBlockTable=function(t,e){switch(e){case d.L:return L.RS_BLOCK_TABLE[(t-1)*4+0];case d.M:return L.RS_BLOCK_TABLE[(t-1)*4+1];case d.Q:return L.RS_BLOCK_TABLE[(t-1)*4+2];case d.H:return L.RS_BLOCK_TABLE[(t-1)*4+3];default:return}};function X(){this.buffer=[],this.length=0}X.prototype={get:function(t){var e=Math.floor(t/8);return(this.buffer[e]>>>7-t%8&1)==1},put:function(t,e){for(var a=0;a<e;a++)this.putBit((t>>>e-a-1&1)==1)},getLengthInBits:function(){return this.length},putBit:function(t){var e=Math.floor(this.length/8);this.buffer.length<=e&&this.buffer.push(0),t&&(this.buffer[e]|=128>>>this.length%8),this.length++}};var R=[[17,14,11,7],[32,26,20,14],[53,42,32,24],[78,62,46,34],[106,84,60,44],[134,106,74,58],[154,122,86,64],[192,152,108,84],[230,180,130,98],[271,213,151,119],[321,251,177,137],[367,287,203,155],[425,331,241,177],[458,362,258,194],[520,412,292,220],[586,450,322,250],[644,504,364,280],[718,560,394,310],[792,624,442,338],[858,666,482,382],[929,711,509,403],[1003,779,565,439],[1091,857,611,461],[1171,911,661,511],[1273,997,715,535],[1367,1059,751,593],[1465,1125,805,625],[1528,1190,868,658],[1628,1264,908,698],[1732,1370,982,742],[1840,1452,1030,790],[1952,1538,1112,842],[2068,1628,1168,898],[2188,1722,1228,958],[2303,1809,1283,983],[2431,1911,1351,1051],[2563,1989,1423,1093],[2699,2099,1499,1139],[2809,2213,1579,1219],[2953,2331,1663,1273]];function j(){return typeof CanvasRenderingContext2D!="undefined"}function V(){var t=!1,e=navigator.userAgent;if(/android/i.test(e)){t=!0;var a=e.toString().match(/android ([0-9]\.[0-9])/i);a&&a[1]&&(t=parseFloat(a[1]))}return t}var Y=function(){var t=function(e,a){this._el=e,this._htOption=a};return t.prototype.draw=function(e){var a=this._htOption,r=this._el,s=e.getModuleCount();Math.floor(a.width/s),Math.floor(a.height/s),this.clear();function i(u,w){var k=document.createElementNS("http://www.w3.org/2000/svg",u);for(var F in w)w.hasOwnProperty(F)&&k.setAttribute(F,w[F]);return k}var o=i("svg",{viewBox:"0 0 "+String(s)+" "+String(s),width:"100%",height:"100%",fill:a.colorLight});o.setAttributeNS("http://www.w3.org/2000/xmlns/","xmlns:xlink","http://www.w3.org/1999/xlink"),r.appendChild(o),o.appendChild(i("rect",{fill:a.colorLight,width:"100%",height:"100%"})),o.appendChild(i("rect",{fill:a.colorDark,width:"1",height:"1",id:"template"}));for(var n=0;n<s;n++)for(var l=0;l<s;l++)if(e.isDark(n,l)){var f=i("use",{x:String(l),y:String(n)});f.setAttributeNS("http://www.w3.org/1999/xlink","href","#template"),o.appendChild(f)}},t.prototype.clear=function(){for(;this._el.hasChildNodes();)this._el.removeChild(this._el.lastChild)},t}(),q=document.documentElement.tagName.toLowerCase()==="svg",J=q?Y:j()?function(){function t(){this._elImage.src=this._elCanvas.toDataURL("image/png"),this._elImage.style.display="block",this._elCanvas.style.display="none"}if(this&&this._android&&this._android<=2.1){var e=1/window.devicePixelRatio,a=CanvasRenderingContext2D.prototype.drawImage;CanvasRenderingContext2D.prototype.drawImage=function(i,o,n,l,f,u,w,k,F){if("nodeName"in i&&/img/i.test(i.nodeName))for(var y=arguments.length-1;y>=1;y--)arguments[y]=arguments[y]*e;else typeof k=="undefined"&&(arguments[1]*=e,arguments[2]*=e,arguments[3]*=e,arguments[4]*=e);a.apply(this,arguments)}}function r(i,o){var n=this;if(n._fFail=o,n._fSuccess=i,n._bSupportDataURI===null){var l=document.createElement("img"),f=function(){n._bSupportDataURI=!1,n._fFail&&n._fFail.call(n)},u=function(){n._bSupportDataURI=!0,n._fSuccess&&n._fSuccess.call(n)};l.onabort=f,l.onerror=f,l.onload=u,l.src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==";return}else n._bSupportDataURI===!0&&n._fSuccess?n._fSuccess.call(n):n._bSupportDataURI===!1&&n._fFail&&n._fFail.call(n)}var s=function(i,o){this._bIsPainted=!1,this._android=V(),this._htOption=o,this._elCanvas=document.createElement("canvas"),this._elCanvas.width=o.width,this._elCanvas.height=o.height,i.appendChild(this._elCanvas),this._el=i,this._oContext=this._elCanvas.getContext("2d"),this._bIsPainted=!1,this._elImage=document.createElement("img"),this._elImage.alt="Scan me!",this._elImage.style.display="none",this._el.appendChild(this._elImage),this._bSupportDataURI=null};return s.prototype.draw=function(i){var o=this._elImage,n=this._oContext,l=this._htOption,f=i.getModuleCount(),u=l.width/f,w=l.height/f,k=Math.round(u),F=Math.round(w);o.style.display="none",this.clear();for(var y=0;y<f;y++)for(var M=0;M<f;M++){var x=i.isDark(y,M),S=M*u,U=y*w;n.strokeStyle=x?l.colorDark:l.colorLight,n.lineWidth=1,n.fillStyle=x?l.colorDark:l.colorLight,n.fillRect(S,U,u,w),n.strokeRect(Math.floor(S)+.5,Math.floor(U)+.5,k,F),n.strokeRect(Math.ceil(S)-.5,Math.ceil(U)-.5,k,F)}this._bIsPainted=!0},s.prototype.makeImage=function(){this._bIsPainted&&r.call(this,t)},s.prototype.isPainted=function(){return this._bIsPainted},s.prototype.clear=function(){this._oContext.clearRect(0,0,this._elCanvas.width,this._elCanvas.height),this._bIsPainted=!1},s.prototype.round=function(i){return i&&Math.floor(i*1e3)/1e3},s}():function(){var t=function(e,a){this._el=e,this._htOption=a};return t.prototype.draw=function(e){for(var a=this._htOption,r=this._el,s=e.getModuleCount(),i=Math.floor(a.width/s),o=Math.floor(a.height/s),n=['<table style="border:0;border-collapse:collapse;">'],l=0;l<s;l++){n.push("<tr>");for(var f=0;f<s;f++)n.push('<td style="border:0;border-collapse:collapse;padding:0;margin:0;width:'+i+"px;height:"+o+"px;background-color:"+(e.isDark(l,f)?a.colorDark:a.colorLight)+';"></td>');n.push("</tr>")}n.push("</table>"),r.innerHTML=n.join("");var u=r.childNodes[0],w=(a.width-u.offsetWidth)/2,k=(a.height-u.offsetHeight)/2;w>0&&k>0&&(u.style.margin=k+"px "+w+"px")},t.prototype.clear=function(){this._el.innerHTML=""},t}();function tt(t,e){for(var a=1,r=et(t),s=0,i=R.length;s<i;s++){var o=0;switch(e){case d.L:o=R[s][0];break;case d.M:o=R[s][1];break;case d.Q:o=R[s][2];break;case d.H:o=R[s][3];break}if(r<=o)break;a++}if(a>R.length)throw new Error("Too long data");return a}function et(t){var e=encodeURI(t).toString().replace(/\%[0-9a-fA-F]{2}/g,"a");return e.length+(e.length!=t?3:0)}return A=function(t,e){if(this._htOption={width:256,height:256,typeNumber:4,colorDark:"#000000",colorLight:"#ffffff",correctLevel:d.H},typeof e=="string"&&(e={text:e}),e)for(var a in e)this._htOption[a]=e[a];typeof t=="string"&&(t=document.getElementById(t)),this._htOption.useSVG&&(J=Y),this._android=V(),this._el=t,this._oQRCode=null,this._oDrawing=new J(this._el,this._htOption),this._htOption.text&&this.makeCode(this._htOption.text)},A.prototype.makeCode=function(t){this._oQRCode=new B(tt(t,this._htOption.correctLevel),this._htOption.correctLevel),this._oQRCode.addData(t),this._oQRCode.make(),this._el.title=t,this._oDrawing.draw(this._oQRCode),this.makeImage()},A.prototype.makeImage=function(){typeof this._oDrawing.makeImage=="function"&&(!this._android||this._android>=3)&&this._oDrawing.makeImage()},A.prototype.clear=function(){this._oDrawing.clear()},A.CorrectLevel=d,A})})(Z);var _t=Z.exports;const Dt={name:"attachDetail",setup(h,T){const{proxy:A}=ct(),D=ut({isShowDialog:!1,myTimer:null,ruleForm:{id:null},rules:{}}),B=p=>{D.isShowDialog=!0,D.ruleForm.id=p},C=()=>{D.isShowDialog=!1,D.myTimer!=null&&clearInterval(Number(D.myTimer))},d=()=>{C(),v()},E=()=>{D.ruleForm.id&&D.ruleForm.id!=null&&nt(D.ruleForm.id).then(p=>{D.ruleForm=p.data})},c=()=>{it(D.ruleForm.id).then(p=>{A.$refs.qrcodeRef.innerHTML="",new _t(A.$refs.qrcodeRef,{text:p,width:125,height:125,colorDark:"#000000",colorLight:"#ffffff"}),D.myTimer=setInterval(()=>{!D.ruleForm.id||ot(D.ruleForm.id).then(b=>{b.code==200&&(C(),v())}).catch(()=>{})},2e3)})};ht(()=>{E()});const v=()=>{D.ruleForm={}};return z({onPayment:c,openDialog:B,closeDialog:C,onCancel:d},ft(D))}},I=h=>(mt("data-v-82f38dd8"),h=h(),vt(),h),At={class:"system-menu-container"},Ct={class:"personal"},Et={class:"personal-user"},wt={class:"personal-user-right"},Tt=I(()=>m("div",{class:"personal-item-label"},"\u8BA2\u5355\u7F16\u53F7\uFF1A",-1)),Bt={class:"personal-item-value"},bt=I(()=>m("div",{class:"personal-item-label"},"\u8BA2\u5355\u72B6\u6001\uFF1A",-1)),Lt={class:"personal-item-value"},kt=I(()=>m("div",{class:"personal-item-label"},"\u4EA4\u6613\u72B6\u6001\uFF1A",-1)),yt={class:"personal-item-value"},Pt=I(()=>m("div",{class:"personal-item-label"},"\u652F\u4ED8\u72B6\u6001\uFF1A",-1)),Ft={class:"personal-item-value"},It=I(()=>m("div",{class:"personal-item-label"},"\u8BA2\u5355\u91D1\u989D\uFF1A",-1)),Mt={class:"personal-item-value"},Rt=I(()=>m("div",{class:"personal-item-label"},"\u4E70\u5BB6\uFF1A",-1)),xt={class:"personal-item-value"},St=I(()=>m("div",{class:"personal-item-label"},"\u521B\u5EFA\u65F6\u95F4\uFF1A",-1)),Nt={class:"personal-item-value"},Ot=I(()=>m("div",{class:"personal-item-label"},"\u4E70\u5BB6\u7559\u8A00\uFF1A",-1)),Ht={class:"personal-item-value"},Ut=I(()=>m("div",{class:"personal-item-label"},"\u5356\u5BB6\u5907\u6CE8\uFF1A",-1)),Qt={class:"personal-item-value"},Gt={class:"personal-edit-safe-item"},Kt={class:"personal-edit-safe-item-left"},Xt={class:"personal-edit-safe-item-left-label"},Vt={class:"personal-edit-safe-item-right"},Yt={class:"mb30 mt30 qrcode-img"},Jt={class:"qrcode",ref:"qrcodeRef"},Wt={class:"dialog-footer"},$t=H("\u652F \u4ED8"),zt=H("\u53D6 \u6D88");function Zt(h,T,A,D,B,C){const d=O("el-col"),E=O("el-row"),c=O("el-card"),v=O("el-button"),p=O("el-dialog");return N(),Q("div",At,[_(p,{title:"\u8BA2\u5355\u8BE6\u60C5",fullscreen:"",modelValue:h.isShowDialog,"onUpdate:modelValue":T[0]||(T[0]=b=>h.isShowDialog=b)},{footer:g(()=>[m("span",Wt,[h.ruleForm.payStatus!=1?(N(),G(v,{key:0,type:"success",onClick:D.onPayment,size:"small"},{default:g(()=>[$t]),_:1},8,["onClick"])):K("",!0),_(v,{onClick:D.onCancel,size:"small"},{default:g(()=>[zt]),_:1},8,["onClick"])])]),default:g(()=>[m("div",Ct,[_(E,null,{default:g(()=>[_(d,{xs:24,sm:24},{default:g(()=>[_(c,{shadow:"hover"},{default:g(()=>[m("div",Et,[m("div",wt,[_(E,null,{default:g(()=>[_(d,{span:24,class:"personal-title mb18"},{default:g(()=>[H(P(h.ruleForm.orderTitle),1)]),_:1}),_(d,{span:24},{default:g(()=>[_(E,null,{default:g(()=>[_(d,{xs:24,sm:8,class:"personal-item mb6"},{default:g(()=>[Tt,m("div",Bt,P(h.ruleForm.orderSn),1)]),_:1}),_(d,{xs:24,sm:4,class:"personal-item mb6"},{default:g(()=>[bt,m("div",Lt,P(h.ruleForm.statusStr),1)]),_:1}),_(d,{xs:24,sm:4,class:"personal-item mb6"},{default:g(()=>[kt,m("div",yt,P(h.ruleForm.tradeStatusStr),1)]),_:1}),_(d,{xs:24,sm:4,class:"personal-item mb6"},{default:g(()=>[Pt,m("div",Ft,P(h.ruleForm.payStatusStr),1)]),_:1})]),_:1})]),_:1}),_(d,{span:24},{default:g(()=>[_(E,null,{default:g(()=>[_(d,{xs:24,sm:8,class:"personal-item mb6"},{default:g(()=>[It,m("div",Mt,P(h.ruleForm.orderAmount),1)]),_:1}),_(d,{xs:24,sm:4,class:"personal-item mb6"},{default:g(()=>[Rt,m("div",xt,P(h.ruleForm.nickName),1)]),_:1}),_(d,{xs:24,sm:4,class:"personal-item mb6"},{default:g(()=>[St,m("div",Nt,P(h.ruleForm.created),1)]),_:1})]),_:1})]),_:1}),h.ruleForm.buyerMsg!=null&&h.ruleForm.buyerMsg!=""?(N(),G(d,{key:0,span:24},{default:g(()=>[_(E,null,{default:g(()=>[_(d,{xs:24,sm:24,class:"personal-item mb6"},{default:g(()=>[Ot,m("div",Ht,P(h.ruleForm.buyerMsg),1)]),_:1})]),_:1})]),_:1})):K("",!0),h.ruleForm.remarks!=null&&h.ruleForm.remarks!=""?(N(),G(d,{key:1,span:24},{default:g(()=>[_(E,null,{default:g(()=>[_(d,{xs:24,sm:24,class:"personal-item mb6"},{default:g(()=>[Ut,m("div",Qt,P(h.ruleForm.remarks),1)]),_:1})]),_:1})]),_:1})):K("",!0)]),_:1})])])]),_:1})]),_:1}),_(d,{span:24},{default:g(()=>[_(c,{shadow:"hover",class:"mt15 personal-edit",header:"\u5546\u54C1\u4FE1\u606F"},{default:g(()=>[(N(!0),Q(dt,null,gt(h.ruleForm.orderItemList,(b,L)=>(N(),Q("div",{class:"personal-edit-safe-box",key:L},[m("div",Gt,[m("div",Kt,[m("div",Xt,P(b.productTitle),1)]),m("div",Vt,[_(v,{type:"text"},{default:g(()=>[H("\u6570\u91CF X "+P(b.productCount),1)]),_:2},1024)])])]))),128))]),_:1})]),_:1})]),_:1})]),m("div",Yt,[m("div",Jt,null,512)])]),_:1},8,["modelValue"])])}var ae=pt(Dt,[["render",Zt],["__scopeId","data-v-82f38dd8"]]);export{ae as default};