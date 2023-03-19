import SockJS from "sockjs-client/dist/sockjs.min.js"
// import Stomp from "stompjs"

const wsUrl = "http://localhost:8080/fastcms/websocket"; // websocket 默认连接地址
let websocket: any; // 用于存储实例化后websocket
let isConnect = false; // 连接标识 避免重复连接
let rec: any; // 断线重连后，延迟5秒重新创建WebSocket连接  rec用来存储延迟请求的代码

// 创建websocket
function creatWebSocket() {
  console.log("websocket url:" + wsUrl);
  // 判断当前浏览器是否支持WebSocket
  if ("WebSocket" in window) {
    console.log("当前浏览器 windows");
  } else if ("MozWebSocket" in window) {
    console.log("当前浏览器 windows");
  } else {
    console.log("当前浏览器 Not support websocket");
  }

  try {
    initWebSocket(); // 初始化websocket连接
  } catch (e) {
    console.log("尝试创建连接失败");
    reConnect(); // 如果无法连接上 webSocket 那么重新连接！可能会因为服务器重新部署，或者短暂断网等导致无法创建连接
  }
}

// 初始化websocket
function initWebSocket() {
  websocket = new SockJS(wsUrl);
  console.log("websocket:", websocket);

  websocket.onopen = function (e: any) {
    websocketOpen(e);
  };

  // 接收
  websocket.onmessage = function (e: any) {
    websocketonmessage(e);
  };

  // 连接发生错误
  websocket.onerror = function () {
    console.log("WebSocket连接发生错误");
    isConnect = false; // 连接断开修改标识
    reConnect(); // 连接错误 需要重连
  };

  websocket.onclose = function (e: any) {
    websocketclose(e);
  };
}

// 定义重连函数
let reConnect = () => {
  console.log("尝试重新连接");
  if (isConnect) return; // 如果已经连上就不在重连了
  rec && clearTimeout(rec);
  rec = setTimeout(function () {
    // 延迟5秒重连  避免过多次过频繁请求重连
    creatWebSocket();
  }, 5000);
};

// 创建连接
function websocketOpen(e: any) {
  console.log("连接成功");
}
// 数据接收
function websocketonmessage(e: any) {
  console.log("数据接收", e.data);

  // let data = JSON.parse(decodeUnicode(e.data))
}
// 关闭
function websocketclose(e: any) {
  console.log(e);
  isConnect = false; // 断开后修改标识
  console.log("connection closed (" + e.code + ")");
}

// 数据发送
function websocketsend(data: any) {
  console.log("发送的数据", data, JSON.stringify(data));
  websocket.send(JSON.stringify(data));
}

// 实际调用的方法==============

// 发送
function sendWebSocket(data: any) {
  if (websocket.readyState === websocket.OPEN) { // 开启状态
    websocketsend(data);
  } else { // 若 未开启 / 正在开启 状态 ，则等待1s后重新调用
    setTimeout(function () {
      sendWebSocket(data);
    }, 1000);
  }
}

// 关闭
let closeWebSocket = () => {
  websocket.close();
};

export {
  sendWebSocket,
  creatWebSocket,
  closeWebSocket,
};