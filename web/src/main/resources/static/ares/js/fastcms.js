function getToken() {
    return token;
}

function getClientId() {
    return sessionStorage.getItem("ClientId") || "";
}

function submitForm(rules, messages, okFunction, failFunction, isJsonRequest) {
    submitFormById("myForm", rules, messages, okFunction, failFunction, isJsonRequest);
}

function submitFormById(formId, rules, messages, okFunction, failFunction, isJsonRequest) {

    $('#'+formId).validate({
        rules: rules,
        messages: messages,
        errorElement: 'span',
        errorPlacement: function (error, element) {
            error.addClass('invalid-feedback');
            element.closest('.form-group').append(error);
        },
        highlight: function (element, errorClass, validClass) {
            $(element).addClass('is-invalid');
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).removeClass('is-invalid');
        },
        submitHandler: function(form) {

            var formData = new FormData($(form)[0]);

            if (isJsonRequest) {
                ajaxJsonPost($(form).attr("action"), convertFormDataToJson(formData), okFunction, failFunction);
            } else {
                ajaxPost($(form).attr("action"), formData, okFunction, failFunction);
            }
            return false;
        }
    });
}

var convertFormDataToJson = function (formData) {
    var objData = {};

    formData.forEach((value, key) => {
        objData[key] = value
    });

    // if ($.isEmptyObject(objData)) {
    //     return ""
    // }

    return JSON.stringify(objData);
};

function ajaxGet(url, okFunction, failFunction) {
    if (url == null || "" == url) {
        alert("url 不能为空 ");
        return
    }

    okFunction = okFunction || function (result) {};

    failFunction = failFunction || function (result) {
        toastr.error(result.message, '操作失败');
    };

    $.ajax({
        url: url,
        type: "get",
        //不使用缓存
        cache: false,
        processData: false,
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", getToken());
        },
        success: function (result) {
            if(result.code && result.code != 200){
                failFunction(result);
            }else {
                okFunction(result);
            }
        },
        error: function (xhr) {
            if(xhr.status == 403) {
                alert("403 forbid")
            } else if(xhr.status == 401) {
                alert("401 not auth")
            } else if(xhr.status == 404) {
                alert("404 not found")
            } else {
                alert("unknow status");
            }
        },
        complete: function (resp) {

        }
    });
}

function ajaxPost(url, data, okFunction, failFunction) {
    if (url == null || "" == url) {
        alert("url 不能为空 ");
        return
    }

    okFunction = okFunction || function (result) {
        alertSuccess("操作成功", function () {
            location.reload();
        })
    };

    failFunction = failFunction || function (result) {
        toastr.error(result.message, '操作失败');
    };

    $.ajax({
        url: url,
        type: "post",
        cache: false,
        processData: false,
        contentType: false,
        data: data,
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", getToken());
        },
        success: function (result) {
            if(result.code != 200) {
                failFunction(result);
            }else {
                okFunction(result);
            }
        },
        error: function (xhr) {
            if(xhr.status == 403) {
                alert("403 forbid")
            } else if(xhr.status == 401) {
                alert("401 not auth")
            } else if(xhr.status == 404) {
                alert("404 not found")
            } else {
                alert("500 error");
            }
        },
        complete: function (resp) {

        }
    });
}

function ajaxJsonPost(url, data, okFunction, failFunction) {
    if (url == null || "" == url) {
        alert("url 不能为空 ");
        return
    }

    okFunction = okFunction || function (result) {
        alertSuccess("操作成功", function () {
            location.reload();
        })
    };

    failFunction = failFunction || function (result) {
        alert(result.message);
    };

    $.ajax({
        url: url,
        type: "post",
        cache: false,
        processData: false,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: data,
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", getToken());
            xhr.setRequestHeader("ClientId", getClientId());
        },
        success: function (result) {
            if(result.code == 200) {
                okFunction(result);
            }else {
                failFunction(result);
            }
        },
        error: function (xhr) {
            if(xhr.status == 403) {
                alert("403 forbid")
            } else if(xhr.status == 401) {
                alert("401 not auth")
            } else {
                alert("unknow status");
            }
        },
        complete: function (resp) {

        }
    });

}

function getConfigFormData(params) {
    var requestParam = "";
    params.forEach(function(v, i){
        if(i ==0) {
            requestParam = "configKeys=" + v;
        }else{
            requestParam += "&configKeys=" + v;
        }
    })

    $.ajax({
        url: "/fastcms/api/admin/config/list",
        type: "post",
        //不使用缓存
        cache: false,
        //发送不想转换的的信息
        processData: false,
        contentType: "application/x-www-form-urlencoded",
        data: requestParam,
        beforeSend: function( xhr ) {
            xhr.setRequestHeader('X-Requested-With', {toString: function(){ return ''; }});
            xhr.setRequestHeader("Authorization", token);
        },
        success: function (data) {
            if(data.code != 200){
                alertError(data.message, function () {});
            }else {
                data.data.forEach(function (obj, index) {
                    $("#"+obj.key).val(obj.value);
                })
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            if(xhr.status==403) {
                alert("403 forbid")
            }
        },
        complete: function (resp) {

        }
    });

}

function alertWarn(message) {
    toastr.warning(message);
}

function alertError(message, callback) {
    toastr.error(message, function () {
        setTimeout(callback, 500);
    });
}

function alertSuccess(message, callback) {
    toastr.success(message, function () {
        setTimeout(callback, 500)
    });
}
