Embulk::JavaPlugin.register_input(
  "box", "org.embulk.input.box.BoxFileInputPlugin",
  File.expand_path('../../../../classpath', __FILE__))
