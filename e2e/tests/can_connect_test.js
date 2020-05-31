module.exports = {
    'Can connect': function (browser) {
        browser.url(browser.launch_url)
            .waitForElementVisible('body',2000)
    }
}