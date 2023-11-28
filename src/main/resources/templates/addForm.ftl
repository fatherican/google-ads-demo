<!DOCTYPE html>
<html>
<head>
    <title>Google ADS Add </title>
    <script type="text/javascript">
        function showLoadingOverlay(message) {
            $(".loading-message").text(message || "加载中...");
            $(".overlay").fadeIn();
        }
        function hideLoadingOverlay() {
            $(".overlay").fadeOut();
        }

        $(document).ready(function () {



        })
        function submitForm() {
            showLoadingOverlay("Submiting...");
            $("#submitBt").attr("disabled", true);
        }
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

    <h1>ADD CAMPAIGN</h1>
    <form name="form" id="form" action="/addAds" method="post" onsubmit="submitForm()" enctype="multipart/form-data">
        <h2>Creativity Section</h2>
        <label class="red">*</label><label for="imageFile">Landscape Image File:</label>
        <div class="tooltip">
            <span>?</span>
            <span class="tooltip-text">因google ads 要求，图片比例必须是1.91:1，可使用代码目录下的示例图asset/landscape.jpeg</span>
        </div>
        <input type="file" id="landScapeImageFile" name="landScapeImageFile" accept=".png, .jpg" required>
        <br>
        <label class="red">*</label><label for="imageFile">Square Image File:</label>
        <div class="tooltip">
            <span>?</span>
            <span class="tooltip-text">因google ads 要求，图片比例必须是1:1，可使用代码目录下的示例图asset/square.jpeg</span>
        </div>
        <input type="file" id="squareImageFile" name="squareImageFile" accept=".png, .jpg" required>
        <br>
        <label class="red">*</label><label for="title">Short Head Line:</label>
        <input type="text" id="shortHeadLine" name="shortHeadLine" required>
        <br>
        <label class="red">*</label><label for="title">Long Head Line:</label>
        <input type="text" id="longHeadLine" name="longHeadLine" required>
        <br>
        <label class="red">*</label><label for="shortDescription">Short Description:</label>
        <input type="text" id="shortDescription" name="shortDescription" required>
        <br>
        <label class="red">*</label><label for="landingPageUrl">URL to Landing Page:</label>
        <input type="url" id="landingPageUrl" name="landingPageUrl" required>
        <br>

        <h2>Promotion Settings Section</h2>
        <label for="keywords">Keywords (comma separated):</label>
        <input type="text" id="keywords" name="keywords" >
        <br>
        <label for="negativeKeywords">Negative Keywords (comma separated):</label>
        <input type="text" id="negativeKeywords" name="negativeKeywords" >
        <br>
        <label for="otherFilters">Some other filters(comma separated):</label>
        <input type="text" id="otherFilters" name="otherFilters">
        <br>
        <div style="text-align: center;margin-top:20px">
            <button type="submit" id="submitBt" >Submit</button>
            <button type="button" onclick="window.location.href='/'">cancel</button>
        </div>
    </form>
</div>
</body>
<script src="https://cdn.bootcss.com/jquery/3.5.1/jquery.min.js"></script>
</html>
