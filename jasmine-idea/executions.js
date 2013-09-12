exports.executions = {
  /.*Spec.js/: function(filename) {
    return {
      main: "org.jasmine.cli.Main",
      arguments: ["--compile-mode", "OFF", filename]
    }
  }
}