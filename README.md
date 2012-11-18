# ViewSource #
This is a little dumb app which uses Android Chrome to display the
source of a Internet page.

## Arch ##
This app register itself as an `android.intent.action.SEND`
handler and thus appears in share menu. When it receives an Intent, it
uses the Chrome special scheme `view-source://` to display source.
