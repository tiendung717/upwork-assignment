
document.addEventListener('DOMContentLoaded', function() {
    // Update the text of div.osinfo to indicate that osinfo is being fetched
    const osInfoDiv = document.querySelector('.osinfo');
    if (osInfoDiv) {
        osInfoDiv.textContent = 'Step 1: Fetching OS information...';
    }
    // Append the result of showOSInfo() function to div.result
    appendToResult(showOSInfo());
});

window._freed_wv_msg = (event, params) => {
    const content = document.createElement('p');
    const timestamp = new Date().toLocaleString();
    content.textContent = timestamp + " " + event + " " + params;
    console.log("_freed_wv_msg received")
    appendToResult(content);
    return event
}

function sendMessageToWebView(event) {
    const freedHandle = window._freed_wv;
    if (freedHandle && typeof freedHandle.sendMessage === "function") {
        freedHandle.sendMessage(event, JSON.stringify("Hello there"));
    }
}

function showOSInfo() {
    const content = document.createElement('p');
    let osInfo = "to be implemented";
    if (typeof Android.getOSInfo === "function") {
        osInfo = Android.getOSInfo();
    }
    content.textContent = "Step 2: Recognized OS as " + osInfo;
    return content;
}

function appendToResult(content) {
    const resultDiv = document.querySelector('.result');
    if (resultDiv) {
        resultDiv.appendChild(content);
    }
}
