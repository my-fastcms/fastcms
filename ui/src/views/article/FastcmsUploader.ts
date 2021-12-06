import axios from 'axios'
import { Session } from '/@/utils/storage';

export default class MyUploadAdapter {
    
    constructor( loader ) {
        // The file loader instance to use during the upload.
        this.loader = loader;
    }

    // Starts the upload process.
    async upload() {
        const data = new FormData();
    //   data.append('typeOption', 'upload_image');
      data.append('files', await this.loader.file);
   
      return new Promise((resolve, reject) => {
        axios({
            url: import.meta.env.VITE_API_URL + "/admin/attachment/upload",
            method: 'post',
            data,
            headers: {
                "Authorization": Session.get('token')// 此处为你定义的token 值(Bearer token 接口认证方式)
            },
            withCredentials: true // true 为不允许带 token, false 为允许，可能会遇到跨域报错：Error: Network Error 弹窗提示
        }).then(res => {
            console.log(res)
            var resData = {}
            resData.default = res.urls
            //注意这里的resData一定要是一个对象，而且外面一定要有一个default设置为图片上传后的url，这是ckeditor的规定格式
            resolve(resData);
        }).catch(error => {
            reject(error)
        });
      });

    }

}