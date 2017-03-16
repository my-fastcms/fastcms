/**
 * Created with JetBrains WebStorm.
 * User: zhy
 * Date: 13-12-17
 * Time: 下午9:42
 * To change this template use File | Settings | File Templates.
 */

function Canvas2D($canvas)
{
    var context = $canvas[0].getContext("2d"),
        width = $canvas[0].width,
        height = $canvas[0].height,
        pageOffset = $canvas.offset();

    context.font = "24px  Verdana, Geneva, sans-serif  ";
    context.textBaseline = "top";

    this.width = function ()
    {
        return width;
    }
    this.height = function ()
    {
        return height;
    }

    this.resetOffset = function ()
    {
        pageOffset = $canvas.offset();
    }

    $(window).resize(function ()
    {
        pageOffset = $canvas.offset();
    });

    this.getCanvasPoint = function (pageX, pageY)
    {
        return{
            x: pageX - pageOffset.left,
            y: pageY - pageOffset.top
        }
    }

    this.clearRect = function (start, end)
    {
        context.clearRect(start.x, start.y, 10, 10);
        return this;
    };

    this.clear = function ()
    {
        context.clearRect(0, 0, width, height);
        return this;
    };

    this.drawLine = function (start, end)
    {
        context.beginPath();
        context.moveTo(start.x, start.y);
        context.lineTo(end.x, end.y);
        context.stroke();

        return this;
    };

    this.drawRect = function (start, end, isFill)
    {
        var w = end.x - start.x , h = end.y - start.y;
        if (isFill)
        {
            context.fillRect(start.x, start.y, w, h);
        }
        else
        {
            context.strokeRect(start.x, start.y, w, h);
        }
    };

    this.drawCircle = function (center, radius, fill)
    {
        context.beginPath();
        context.arc(center.x, center.y, radius, 0, Math.PI * 2, true);
        if (fill)
            context.fill();
        else
            context.stroke();
    };

    this.drawPoints = function (points)
    {
        context.beginPath();
        context.moveTo(points[0].x, points[0].y);

        for (var i = 1; i < points.length; i++)
        {
            context.lineTo(points[i].x, points[i].y);
        }
        context.stroke();
        return this;
    };

    this.drawText = function (text, point, fill)
    {
        var metrics = context.measureText(text);
        console.log(metrics);
        if (fill)
        {
            context.fillText(text, point.x, point.y);
        }
        else
        {
            context.strokeText(text, point.x, point.y);
        }
    };

    this.drawEllipse = function (center, end, fill)
    {
        var rx = Math.abs(end.x - center.x);
        var ry = Math.abs(end.y - center.y);

        var radius = Math.sqrt(rx * rx, ry * ry);

        context.save();
        context.translate(center.x, center.y);
        context.scale(rx / radius, ry / radius);
        context.beginPath();
        context.arc(0, 0, radius, 0, Math.PI * 2, true);
        context.closePath();
        if (!fill)
            context.stroke();
        else
            context.fill();

        context.restore();
    };

    this.penWidth = function (newWidth)
    {
        if (arguments.length)
        {
            context.lineWidth = newWidth;
            return this;
        }
        return context.lineWidth;
    };

    this.penColor = function (newColor)
    {
        if (arguments.length)
        {
            context.strokeStyle = newColor;
            context.fillStyle = newColor;
            return this;
        }

        return context.strokeStyle;
    };

    this.penOpacity = function (newOpacity)
    {
        if (arguments.length)
        {
            context.globalAlpha = newOpacity;
            return this;
        }
        return context.globalAlpha;
    };

    this.fontSize = function (fontSize)
    {
        if (arguments.length)
        {
            context.font = fontSize + "px Verdana, Geneva, sans-serif";
            return this;
        }

        return context.fontSize;
    }


    this.savePen = function ()
    {
        context.save();
        return this;
    };
    this.restorePen = function ()
    {
        context.restore();
        return this;
    };

}