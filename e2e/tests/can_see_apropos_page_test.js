module.exports = {
    'Verify A Propos content is shown': function (browser) {
        browser.url('https://ivvq-patterns.herokuapp.com/#/aPropos')
            .waitForElementVisible('body',2000)
            .assert.elementPresent('p.text')
            .assert.elementPresent('h1.title')
            .end()
    }
}