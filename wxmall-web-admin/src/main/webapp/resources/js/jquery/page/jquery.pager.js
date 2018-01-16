/*
* jQuery pager plugin
* Version 1.0 (12/22/2008)
* @requires jQuery v1.2.6 or later
*
* Example at: http://jonpauldavies.github.com/JQuery/Pager/PagerDemo.html
*
* Copyright (c) 2008-2009 Jon Paul Davies
* Dual licensed under the MIT and GPL licenses:
* http://www.opensource.org/licenses/mit-license.php
* http://www.gnu.org/licenses/gpl.html
* 
* Read the related blog post and contact the author at http://www.j-dee.com/2008/12/22/jquery-pager-plugin/
*
* This version is far from perfect and doesn't manage it's own state, therefore contributions are more than welcome!
*
* Usage: .pager({ pagenumber: 1, pagecount: 15, buttonClickCallback: PagerClickTest });
*
* Where pagenumber is the visible page number
*       pagecount is the total number of pages to display
*       buttonClickCallback is the method to fire when a pager button is clicked.
*
* buttonClickCallback signiture is PagerClickTest = function(pageclickednumber) 
* Where pageclickednumber is the number of the page clicked in the control.
*
* The included Pager.CSS file is a dependancy but can obviously tweaked to your wishes
* Tested in IE6 IE7 Firefox & Safari. Any browser strangeness, please report.
*/
(function($) {

    $.fn.pager = function(options) {

        var opts = $.extend({}, $.fn.pager.defaults, options);
        var pgcount = getPageCount(opts.recordcount, opts.pagesize);
        return this.each(function() {

            // empty out the destination element and then render out the pager with the supplied options
            $(this).empty().append(renderpager(opts.table, parseInt(opts.pagenumber), pgcount, opts.recordcount, opts.buttonClickCallback,
            		opts.firsttext, opts.prevtext, opts.nexttext, opts.lasttext, opts.recordtext, opts.gotext));

            // specify correct cursor activity
            $(' li', this).mouseover(function() { document.body.style.cursor = "pointer"; }).mouseout(function() { document.body.style.cursor = "auto"; });
        });
    };

    function getPageCount(totalCount, pageSize) {
        var pageCount = 0;
        pageCount = parseInt(totalCount / pageSize);
        if (totalCount % pageSize > 0) {
            pageCount++;
        }
        return pageCount;
    }

    // render and return the pager with the supplied options
    function renderpager(table, pagenumber, pagecount, recordcount, buttonClickCallback, firsttext, prevtext, nexttext, lasttext, recordtext, gotext) {

        // setup $pager to hold render
        var $pager = $('<ul class="pages"></ul>');

        // add in the previous and next buttons
        $pager.append(renderButton(table, 'first', firsttext, pagenumber, pagecount, buttonClickCallback)).append(renderButton(table, 'prev', prevtext, pagenumber, pagecount, buttonClickCallback));

        // pager currently only handles 10 viewable pages ( could be easily parameterized, maybe in next version ) so handle edge cases
        var startPoint = 1;
        var endPoint = 5;
        if (pagenumber > pagecount) {
            pagenumber = pagecount;
        }
        if (pagenumber > 4) {
            startPoint = pagenumber - 4;
            endPoint = pagenumber + 4;
        }

        if (endPoint > pagecount) {
            startPoint = pagecount - 8;
            endPoint = pagecount;
        }

        if (startPoint < 1) {
            startPoint = 1;
        }

        // loop thru visible pages and render buttons
        for (var page = startPoint; page <= endPoint; page++) {

            var currentButton = $('<li class="page-number">' + (page) + '</li>');

            page == pagenumber ? currentButton.addClass('pgCurrent') : currentButton.click(function() { buttonClickCallback(table, this.firstChild.data); });
            currentButton.appendTo($pager);
        }

        // render in the next and last buttons before returning the whole rendered control back.
        $pager.append(renderButton(table, 'next', nexttext, pagenumber, pagecount, buttonClickCallback)).append(renderButton(table, 'last', lasttext, pagenumber, pagecount, buttonClickCallback));
        if (pagecount > 9) {
            $pager.append('<li class="pgText"><input tyle="text" class="iptGo" /><span class="spGo">' + gotext + '</span></li>');
            $('.iptGo', $pager).change(function() {
                var num = parseInt($(this).val());
                if (num && num > 0) {
                    if (num > pagecount) {
                        num = pagecount;
                    }
                    $(this).val(num);
                }
                else {
                    $(this).val('');
                }
            }).keyup(function() { $(this).change(); });
            $('.spGo', $pager).click(function() {
                var num = $('.iptGo', $pager).val();
                if (num != '') {
                    buttonClickCallback(table, num);
                }
            });
        }
        if (recordtext != '') {
            $pager.append('<li class="pgText">' + recordtext.replace(/\{0\}/g, pagecount).replace(/\{1\}/g, recordcount) + '</li>');
        }
        return $pager;
    }

    // renders and returns a 'specialized' button, ie 'next', 'previous' etc. rather than a page number button
    function renderButton(table, buttonLabel, buttonText, pagenumber, pagecount, buttonClickCallback) {

        var $Button = $('<li class="pgNext">' + buttonText + '</li>');

        var destPage = 1;

        // work out destination page for required button type
        switch (buttonLabel) {
            case "first":
                destPage = 1;
                break;
            case "prev":
                destPage = pagenumber - 1;
                break;
            case "next":
                destPage = pagenumber + 1;
                break;
            case "last":
                destPage = pagecount;
                break;
        }

        // disable and 'grey' out buttons if not needed.
        if (buttonLabel == "first" || buttonLabel == "prev") {
            pagenumber <= 1 ? $Button.addClass('pgEmpty') : $Button.click(function() { buttonClickCallback(table, destPage); });
        }
        else {
            pagenumber >= pagecount ? $Button.addClass('pgEmpty') : $Button.click(function() { buttonClickCallback(table, destPage); });
        }

        return $Button;
    }

    // pager defaults. hardly worth bothering with in this case but used as placeholder for expansion in the next version
    $.fn.pager.defaults = {
        pagenumber: 1,
        recordcount: 0,
        pagesize: 10,
        firsttext: '首页',//显示的第一页文本
        prevtext: '上一页',//显示的前一页文本
        nexttext: '下一页',//显示的下一页文本
        lasttext: '末页',//显示的最后一页文本
        gotext: 'go',//显示的快速跳转文本
        recordtext: ''//显示记录数，为空时不显示，否则按照占位符显示文本，{0}表示总页数，{1}表示总记录数
    };

})(jQuery);