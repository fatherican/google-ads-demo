<!DOCTYPE html>
<html>
<head>
    <title>Google ADS Add Error</title>
    <script type="text/javascript">

    </script>
    <style type="text/css">
        body {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh; /* 使得内容在屏幕垂直方向上居中 */
            margin: 0;
            flex-direction: column;
        }
        .container {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh; /* 使得内容在屏幕垂直方向上居中 */
            margin: 0;
            flex-direction: column;
            width: 80%; /* 调整页面宽度，根据需要修改 */
            max-width: 1200px; /* 设置最大宽度，防止页面过宽 */
        }
        .red {
            color: red;
        }
        .tooltip {
            position: relative;
            display: inline-block;
            cursor: pointer;
            border-radius: 50%;
            width: 20px;
            height: 20px;
            background-color: #3498db;
            color: #fff;
            text-align: center;
            font-size: 14px;
            box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2);
        }

        .tooltip-text {
            visibility: hidden;
            width: 220px;
            background-color: #555;
            color: #fff;
            text-align: center;
            border-radius: 6px;
            padding: 5px;
            position: absolute;
            z-index: 1;
            bottom: 125%;
            left: 50%;
            margin-left: -60px;
            opacity: 0;
            transition: opacity 0.3s;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
        }

        .tooltip:hover .tooltip-text {
            visibility: visible;
            opacity: 1;
        }

        .overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(255, 255, 255, 0.8); /* 半透明白色背景 */
            display: none;
            justify-content: center;
            align-items: center;
            z-index: 1000; /* 确保遮罩层在其他元素之上 */
        }

        .loading-message {
            font-size: 20px;
            color: #333;
        }
    </style>
</head>
<body>
<div class="container">

    <h1 style="color: red">ADD CAMPAIGN ERROR</h1>
    <#list errorList as error>
        <div>${error}</div></br>
    </#list>
    <button type="button" onclick="window.location.href='/'">To Index Page</button>
</div>
</body>
<script src="https://cdn.bootcss.com/jquery/3.5.1/jquery.min.js"></script>
</html>
