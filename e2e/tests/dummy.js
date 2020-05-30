module.exports = {
  'Dummy test': function (browser) {
    browser.url(browser.launch_url)
      .waitForElementVisible('body', 2000)
      .verify.title('Waifu Market-desu')
      .end()
  }
}
