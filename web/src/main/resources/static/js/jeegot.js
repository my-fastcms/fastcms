var fastcms = {};
fastcms.spath = "/static/";
$(document).ready(function () {
    initMenu();
    initSwitchery();
    initLayerComponents();
    initImageBrowserButton();
    initlayer();
    initDatePicker();
    initDatetimePicker();
})

function initDatePicker() {
    if ($('').datepicker) {
        $('.fastcms-datepicker').datetimepicker({
            timepicker:false,
            format: 'Y-m-d'
        });
    }
}

function initDatetimePicker() {
    if ($('').datetimepicker) {
        $('.fastcms-datetimepicker').datetimepicker({
            format: 'Y-m-d H:m:s'
        });
    }
}

function find(str,cha,num){
    var x=str.indexOf(cha);
    for(var i=0;i<num;i++){
        x=str.indexOf(cha,x+1);
    }
    return x;
}

function initMenu() {
    var pathName = location.pathname;
    if ("/admin" == pathName || "/admin/" == pathName) {
        pathName = "/admin/index";
    }
    var count = pathName.split("/").length-1;
    var activeTreeview, activeLi, activeA;
    $("#sidebar-menu").children().each(function () {
        var li = $(this);
        li.find('a').each(function () {
            var href = $(this).attr("href");
            var _subHref = href.substr(0, find(pathName, "/",count>3 ? count-1 : 2));
            var _subPathName = pathName=="/admin/index" ? pathName : pathName.substr(0, find(pathName, "/",count>3 ? count-1 : 2));
            if (pathName == href) {
                activeTreeview = li;
                activeA = $(this);
                activeLi = $(this).parent();
                return false;
            } else if (pathName.indexOf(href) == 0) {
                activeTreeview = li;
                activeLi = $(this).parent();
                activeA = $(this);
            } else if (_subPathName == _subHref) {
                activeTreeview = li;
                activeLi = $(this).parent();
                activeA = $(this);
            }
        });
    });
    if (activeTreeview) {
        activeTreeview.addClass("menu-open");
        activeTreeview.addClass("active");
    }
    if (activeLi) {
        activeLi.addClass("active");
    }
    if (activeA) {
        activeA.addClass("active");
    }
}

function initlayer() {
    if (typeof layer != "undefined") {
        layer.data = {};
        layer.config({
            extend: 'fastcms/style.css',
            skin: 'layer-ext-fastcms'
        });
    }
}

function initLayerComponents() {
    $("[data-opentype='layer']").on("click",function (component) {

        var layerOptions = {
            type: component.delegateTarget.dataset.layerType|| 2,
            title: component.delegateTarget.dataset.layerTitle || '选择内容',
            anim: component.delegateTarget.dataset.layerAnim || 2,
            shadeClose: component.delegateTarget.dataset.layerShadeClose ? (/^true$/i).test(component.delegateTarget.dataset.layerShadeClose) : true,
            shade: component.delegateTarget.dataset.layerShade || 0.5,
            area: component.delegateTarget.dataset.layerArea ? eval(component.delegateTarget.dataset.layerArea) : ['90%', '90%'],
            content: component.delegateTarget.dataset.layerContent || component.attr('href'),
            end: function () {
                var endFunction = component.delegateTarget.dataset.layerEnd;
                if ("reload" == endFunction) {
                    location.reload();
                } else if (endFunction) {
                    eval(endFunction)(layer);
                }
            }
        };

        layer.open(layerOptions);
    })
}


function initImageBrowserButton() {
    $(".btn-image-browser").on("click", function () {
        var imgBrowserBtn = $(this);
        layer.open({
            type: 2,
            title: '选择图片',
            anim: 2,
            shadeClose: true,
            shade: 0.5,
            area: ['90%', '90%'],
            content: '/admin/attachment/browse',
            end: function () {
                if (layer.data.src != null) {
                    var img = imgBrowserBtn.attr("for-src");
                    var input = imgBrowserBtn.attr("for-input");
                    $("#" + img).attr("src", fastcms.spath + layer.data.src);
                    $("#" + img).trigger("srcChanged", fastcms.spath + layer.data.src);
                    $("#" + input).val(layer.data.src);
                    $("#" + input).trigger("valChanged",layer.data.src);
                }
            }
        });
    })
}

function initSwitchery(config) {
    if (typeof Switchery == "undefined") {
        return;
    }
    var elems = Array.prototype.slice.call(document.querySelectorAll('.switchery'));
    elems.forEach(function (elem) {
        var switchery = config ? new Switchery(elem, config) : new Switchery(elem, {size: 'small'});
        var datafor = elem.getAttribute("data-for");
        if (datafor) {
            $("#" + datafor).val(elem.checked);
            elem.onchange = function () {
                $("#" + datafor).val(elem.checked);
            }
        }
    });

    $(".switchery").change(function () {
        var checked = $(this).is(":checked");
        $(this).val(checked ? 1 : 0);
    })

}

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
                        location.href = "/"+fastcms.visit+"/login"
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

var _dialogShowEvent;

function initEditor(editor, height, type) {
    height = height || 467;
    type = type || 'html'; //默认用ckeditor

    if (type == 'html') {
        return initCkEdtior(editor, height);
    } else if (type == 'markdown') {
        return initMarkdownEditor(editor, height);
    }
}

var commandkeydown = false;

function doListenerCtrlsAndCommands(func) {
    $(document).keydown(function (e) {
        if (e.keyCode == 91 || e.keyCode == 224) {
            commandkeydown = true;
        }
        if (commandkeydown && e.keyCode == 83) {
            commandkeydown = false;
            func();
            return false;
        }
        if (e.ctrlKey == true && e.keyCode == 83) {
            console.log('ctrl+s');
            func();
            return false;
        }
    });
    $(document).keyup(function (e) {
        if (e.keyCode == 91 || e.keyCode == 224) {
            commandkeydown = false;
        }
    });
}

function initCkEdtior(editor, height) {
    CKEDITOR.config.toolbar =
        [
            ['Bold', 'Italic', 'Underline', 'Strike', 'RemoveFormat'],
            ['Blockquote', 'CodeSnippet', 'Image', 'Html5audio', 'Html5video', 'Flash', 'Table', 'HorizontalRule'],
            ['Link', 'Unlink', 'Anchor'],
            ['Outdent', 'Indent'],
            ['NumberedList', 'BulletedList'],
            ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
            '/',
            ['Format', 'FontSize'],
            ['TextColor', 'BGColor'],
            ['Undo', 'Redo'],
            ['Maximize', 'Source']
        ];

    CKEDITOR.config.wordcount = {
        showCharCount: true,
    };

    CKEDITOR.config.disallowedContent = 'img{width,height};img[width,height]';
    CKEDITOR.addCss('.cke_editable img{max-width: 95%;}');

    var ed = CKEDITOR.replace(editor, {
        autoUpdateElement: true,
        removePlugins: 'easyimage,cloudservices',
        extraPlugins: 'entities,codesnippet,uploadimage,flash,image,wordcount,notification,html5audio,html5video,widget,widgetselection,clipboard,lineutils',
        codeSnippet_theme: 'monokai_sublime',
        height: height,
        uploadUrl: '/admin/attachment/doUploadOfCKEditor',
        imageUploadUrl: '/admin/attachment/doUploadOfCKEditor',
        filebrowserUploadUrl: '/admin/attachment/doUploadOfCKEditor',
        filebrowserBrowseUrl: '/admin/attachment/browse',
        language: 'zh-cn'
    });

    ed.on('instanceReady', function () {
        ed.setKeystroke(CKEDITOR.ALT.CTRL + 83, 'save'); //  Ctrl+s
        ed.setKeystroke(1114195, 'save'); // mac command +s
        // 扩展CKEditor的 ctrl + s 保存命令,方便全屏编辑时快捷保存
        ed.addCommand('save', {
            exec: function () {
                var ds = window.doSubmit;
                ds && ds();
            }
        });
    });

    ed.on("dialogShow", function (event) {
        // 方便调试
        _dialogShowEvent = event;

        var infoEle = event.data.getContentElement("info", "browse");
        if (infoEle) infoEle.removeAllListeners();

        var linkEle = event.data.getContentElement("Link", "browse");
        if (linkEle) linkEle.removeAllListeners();

        $(".cke_dialog_ui_button").each(function () {
            if ("浏览服务器" == $(this).text()) {
                $(this).off("click");
                $(this).on("click", function (e) {
                    e.stopPropagation();
                    openlayer(event);
                    return false;
                })
            } else {
                $(this).off("click");
            }
        })

    });

    return ed;
}

function initMarkdownEditor(editor, height) {
    var simpleMDE = new SimpleMDE({
        element: $(editor)[0],
        autoDownloadFontAwesome: false,
        spellChecker: false,
        styleSelectedText: false,
        forceSync: true,
        renderingConfig: {
            codeSyntaxHighlighting: true
        },
        toolbar: [
            "heading", "bold", "italic", "|"
            , "quote", "unordered-list", "ordered-list", "|"
            , "code", "table", "horizontal-rule", "|"
            , "link", {
                name: "image",
                action: function customFunction(editor) {
                    openlayerBySimplemde(editor);
                },
                className: "fa fa-picture-o",
                title: "插入图片",
            }, "|"
            , "preview", "side-by-side", "fullscreen"
        ]

    });
    // 设置markdown编辑器滚动条高度
    $('.CodeMirror-scroll').css({
        "min-height": height
    });
    return simpleMDE;
}

function openlayerBySimplemde(editor) {
    layer.data.src = null;

    layer.open({
        type: 2,
        title: '选择图片',
        anim: 2,
        shadeClose: true,
        shade: 0.5,
        area: ['90%', '90%'],
        content: '/admin/attachment/browse',
        end: function () {
            if (layer.data.src != null) {
                editor.codemirror.replaceSelection('![](' + fastcms.spath + layer.data.src + ')')
            }
        }
    });
}


function openlayer(event) {
    layer.data.src = null;
    layer.open({
        type: 2,
        title: '选择图片',
        anim: 2,
        shadeClose: true,
        shade: 0.5,
        area: ['90%', '90%'],
        content: '/admin/attachment/browse',
        end: function () {
            if (layer.data.src != null) {
                var src = fastcms.spath + layer.data.src;

                var infoTxtUrlEle = event.data.getContentElement('info', 'txtUrl');
                if (infoTxtUrlEle) infoTxtUrlEle.setValue(src);

                var infoUrlEle = event.data.getContentElement('info', 'url');
                if (infoUrlEle) infoUrlEle.setValue(src);

                var infoSrcEle = event.data.getContentElement('info', 'src');
                if (infoSrcEle) infoSrcEle.setValue(src);

                var linkTxtUrlEle = event.data.getContentElement('Link', 'txtUrl')
                if (linkTxtUrlEle) linkTxtUrlEle.setValue(src);
            }
        }
    });
}

