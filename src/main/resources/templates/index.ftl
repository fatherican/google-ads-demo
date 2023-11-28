<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ADS and Keywords</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 0;
        }

        .container {
            width: 80%; /* 调整页面宽度，根据需要修改 */
            max-width: 1200px; /* 设置最大宽度，防止页面过宽 */
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .refresh-button,
        .add-button {
            margin-left: 10px;
            padding: 5px 10px;
            cursor: pointer;
        }

        .refresh-button:hover,
        .add-button:hover {
            background-color: #ddd;
        }
        .align-opdiv {
            display: flex;
            justify-content: space-between;
            align-content: center;
            align-items: baseline;
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
    <div class="align-opdiv">
        <h2>ADS</h2>
        <div class="header-buttons">
            <button class="refresh-button" id="refreshCampaignBt">Refresh</button>
            <button class="add-button" id="addCampaignBt" >Add Campaign</button>
        </div>
    </div>

    <table>
        <thead>
        <tr>
            <th>Ad</th>
            <th>Campaign</th>
            <th>Ad Group</th>
            <th>Landing Page Url</th>
            <th>Short Head Line</th>
            <th>Long Head Line</th>
            <th>Short Description</th>
            <th>status</th>
            <!-- 添加其他需要的列 -->
        </tr>
        </thead>
        <tbody id="adsTBody">
        </tbody>
    </table>
    <div class="align-opdiv">
        <h2>Keywords</h2>
        <div class="header-buttons">
            <button class="refresh-button" id="keywordRefreshBt">Refresh</button>
        </div>
    </div>
    <table>
        <thead>
        <tr>
            <th>Keyword</th>
            <th>Negative</th>
            <th>Match Type</th>
            <th>Campaign</th>
            <th>Ad Group</th>
            <th>Status</th>
            <!-- 添加其他需要的列 -->
        </tr>
        </thead>
        <tbody id="keywordTBody">
        <!-- 添加每行的数据，根据实际情况填充 -->
        <!-- 可以根据实际情况添加更多行 -->
        </tbody>
    </table>
</div>

<div class="overlay">
    <div class="loading-message"></div>
</div>
</body>
<script src="https://cdn.bootcss.com/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {

        function showLoadingOverlay(message) {
            $(".loading-message").text(message || "加载中...");
            $(".overlay").fadeIn();
        }
        function hideLoadingOverlay() {
            $(".overlay").fadeOut();
        }

        $("#addCampaignBt").click(function () {
            window.location="./addAdsPage"
        });

        $("#refreshCampaignBt").click(function () {
            queryAds();
        });

        $("#keywordRefreshBt").click(function () {
            queryKeywords();
        });



        function queryAds() {
            showLoadingOverlay("Loading...");
            $.ajax({
                url: '/queryAllAds',
                type: 'POST',
                contentType: 'application/json',
                success: function(response) {
                    // 请求成功的处理逻辑
                    console.log('成功:', response);
                    $("#adsTBody").empty();
                    let tbodyStr = ""
                    for (const item of response) {
                        // 在这里可以访问 item 的属性，做你想做的事情
                        tbodyStr = tbodyStr +
                            "<tr>" +
                            "<td>" + item.adResourceName + "</td>" +
                            "<td>" + item.campaignName + "</td>" +
                            "<td>" + item.adGroupName + "</td>" +
                            "<td><a target='_blank' href='" + item.landingPageUrl + "'>" + item.landingPageUrl + "</a></td>" +
                            "<td>" + item.shortHeadLine + "</td>" +
                            "<td>" + item.longHeadLine + "</td>" +
                            "<td>" + item.shortDescription + "</td>" +
                            "<td>" + item.status + "</td>" +
                            "</tr>";
                    }
                    $("#adsTBody").html(tbodyStr);
                },
                error: function(error) {
                    // 请求失败的处理逻辑
                    console.log('失败:', error);
                },
                complete: function () {
                    hideLoadingOverlay();
                }
            });
        }

        function queryKeywords() {
            showLoadingOverlay("Loading...");
            $.ajax({
                url: '/queryAllKeywords',
                type: 'POST',
                contentType: 'application/json',
                success: function(response) {
                    $("#keywordTBody").empty();
                    let tbodyStr = ""
                    // 请求成功的处理逻辑
                    for (const item of response) {
                        tbodyStr = tbodyStr +
                            "<tr>" +
                            "<td>" + item.keyword + "</td>" +
                            "<td>" + item.negative + "</td>" +
                            "<td>" + item.matchType + "</td>" +
                            "<td>" + item.campaignName + "</td>" +
                            "<td>" + item.adGroupName + "</td>" +
                            "<td>" + item.status + "</td>" +
                            "</tr>";
                    }
                    $("#keywordTBody").html(tbodyStr);
                },
                error: function(error) {
                    // 请求失败的处理逻辑
                    console.log('失败:', error);
                },
                complete: function () {
                    hideLoadingOverlay();
                }
            });
        }
        queryAds();
        queryKeywords();
    })
</script>
</html>
