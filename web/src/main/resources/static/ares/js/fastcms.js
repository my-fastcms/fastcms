function submitForm(rules, messages, callback, beforeRequest) {

    $('#myForm').validate({
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
            if (typeof (CKEDITOR) != "undefined") {
                for (instance in CKEDITOR.instances) {
                    CKEDITOR.instances[instance].updateElement();
                }
            }

            if(beforeRequest) {
                try {
                    beforeRequest();
                } catch (e) {
                }
            }

            $(form).find(":submit").attr("disabled", true);
            var formData = new FormData($(form)[0]);
            $.ajax({
                url: $(form).attr("action"),
                type: "post",
                //不使用缓存
                cache: false,
                //发送不想转换的的信息
                processData: false,
                contentType: false,
                data: formData,
                success: function (data) {
                    if(data.code == 0){
                        alertError(data.message, function () {
                            if(callback) callback(data)
                        });
                    }else {
                        alertSuccess("操作成功", function () {
                            if(callback) callback(data);
                            var href = $(form).attr("ok");
                            if(href != null && href !="") {
                                location.href = href;
                            }
                        });
                    }
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    if(xhr.status==403) {
                        alert("403 forbid")
                    }
                },
                complete: function (resp) {
                    $(form).find(":submit").attr("disabled", false);
                }
            });
            return false;
        }
    });
}

function ajaxGet(url, okFunction, failFunction) {
    if (url == null || "" == url) {
        alert("url 不能为空 ");
        return
    }

    okFunction = okFunction || function (result) {
        location.reload();
    };

    failFunction = failFunction || function (result) {
        toastr.error(result.message, '操作失败');
    };

    $.get(url, function (result) {
        if (result.code == 1) {
            okFunction(result);
        } else {
            failFunction(result);
        }
    });
}

function ajaxPost(url, data, okFunction, failFunction) {
    if (url == null || "" == url) {
        alert("url 不能为空 ");
        return
    }

    okFunction = okFunction || function (result) {
        location.reload();
    };

    failFunction = failFunction || function (result) {
        toastr.error(result.message, '操作失败');
    };

    $.post(url, data,function (result) {
        if (result.code == 1) {
            okFunction(result);
        } else {
            failFunction(result);
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
