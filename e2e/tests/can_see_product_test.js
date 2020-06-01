module.exports = {
  'Launch app on home page': function (browser) {
    browser.url(browser.launch_url)
      .waitForElementVisible('body', 2000)
  },
  'Verify that products are visible': function (browser) {
    browser
      .assert.elementsCount('div.card', 5)
      .end()
  }
}
