<template>
  <div>
    <md-content>
      f you haven’t given much thought to what makes a great Git commit message, it may be the case that you haven’t spent much time using git log and related tools. There is a vicious cycle here: because the commit history is unstructured and inconsistent, one doesn’t spend much time using or taking care of it. And because it doesn’t get used or taken care of, it remains unstructured and inconsistent.

      But a well-cared for log is a beautiful and useful thing. git blame, revert, rebase, log, shortlog and other subcommands come to life. Reviewing others’ commits and pull requests becomes something worth doing, and suddenly can be done independently. Understanding why something happened months or years ago becomes not only possible but efficient.

      A project’s long-term success rests (among other things) on its maintainability, and a maintainer has few tools more powerful than his project’s log. It’s worth taking the time to learn how to care for one properly. What may be a hassle at first soon becomes habit, and eventually a source of pride and productivity for all involved.

      In this post, I am addressing just the most basic element of keeping a healthy commit history: how to write an individual commit message. There are other important practices like commit squashing that I am not addressing here. Perhaps I’ll do that in a subsequent post.

      Most programming languages have well-established conventions as to what constitutes idiomatic style, i.e. naming, formatting and so on. There are variations on these conventions, of course, but most developers agree that picking one and sticking to it is far better than the chaos that ensues when everybody does their own thing.

      A team’s approach to its commit log should be no different. In order to create a useful revision history, teams should first agree on a commit message convention that defines at least the following three things:

      Style. Markup syntax, wrap margins, grammar, capitalization, punctuation. Spell these things out, remove the guesswork, and make it all as simple as possible. The end result will be a remarkably consistent log that’s not only a pleasure to read but that actually does get read on a regular basis.

      Content. What kind of information should the body of the commit message (if any) contain? What should it not contain?

      Metadata. How should issue tracking IDs, pull request numbers, etc. be referenced?

      Fortunately, there are well-established conventions as to what makes an idiomatic Git commit message. Indeed, many of them are assumed in the way certain Git commands function. There’s nothing you need to re-invent. Just follow the seven rules below and you’re on your way to committing like a pro.
      The seven rules of a great Git commit message

      Keep in mind: This has all been said before.

      Separate subject from body with a blank line
      Limit the subject line to 50 characters
      Capitalize the subject line
      Do not end the subject line with a period
      Use the imperative mood in the subject line
      Wrap the body at 72 characters
      Use the body to explain what and why vs. how

      For example:

      Summarize changes in around 50 characters or less

      More detailed explanatory text, if necessary. Wrap it to about 72
      characters or so. In some contexts, the first line is treated as the
      subject of the commit and the rest of the text as the body. The
      blank line separating the summary from the body is critical (unless
      you omit the body entirely); various tools like `log`, `shortlog`
      and `rebase` can get confused if you run the two together.

      Explain the problem that this commit is solving. Focus on why you
      are making this change as opposed to how (the code explains that).
      Are there side effects or other unintuitive consequences of this
      change? Here's the place to explain them.

      Further paragraphs come after blank lines.

      - Bullet points are okay, too

      - Typically a hyphen or asterisk is used for the bullet, preceded
      by a single space, with blank lines in between, but conventions
      vary here

      If you use an issue tracker, put references to them at the bottom,
      like this:

      Resolves: #123
      See also: #456, #789

      1. Separate subject from body with a blank line

      From the git commit manpage:

      Though not required, it’s a good idea to begin the commit message with a single short (less than 50 character) line summarizing the change, followed by a blank line and then a more thorough description. The text up to the first blank line in a commit message is treated as the commit title, and that title is used throughout Git. For example, Git-format-patch(1) turns a commit into email, and it uses the title on the Subject line and the rest of the commit in the body.

      Firstly, not every commit requires both a subject and a body. Sometimes a single line is fine, especially when theLorem ipsum dolor sit amet, consectetur adipisicing elit. Error quibusdam, non molestias et! Earum magnam, similique, quo recusandae placeat dicta asperiores modi sint ea.
    </md-content>
  </div>
</template>

<script>export default {name: 'HomePage'}
</script>

<style scoped>

</style>
