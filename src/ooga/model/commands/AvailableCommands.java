package ooga.model.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class AvailableCommands {

  private final Map<String, List<Map<String, List<String>>>> commandsMap;
  private final Map<String, Map<String, Integer>> parametersIndex;

  public AvailableCommands(Map<String, List<Map<String, List<String>>>> commandsMap) {
    this.commandsMap = commandsMap;
    parametersIndex = new HashMap<>();
    initializeParametersIndex();
  }

  public Set<String> getCommandNames() {
    return Collections.unmodifiableSet(commandsMap.keySet());
  }

  public List<String> getParameters(String command) {
    List<String> parameterNames = new ArrayList<>();
    commandsMap.get(command).forEach(parameters -> parameterNames.addAll(parameters.keySet()));
    return Collections.unmodifiableList(parameterNames);
  }

  public List<String> getParameterOptions(String command, String parameter) {
    return Collections.unmodifiableList(commandsMap.get(command).get(parametersIndex.get(command).get(parameter)).get(parameter));
  }

  private void initializeParametersIndex() {
    commandsMap.forEach((command, parametersMap) -> {
      Map<String, Integer> parameterIndex = new HashMap<>();
      for (int i = 0; i < parametersMap.size(); i++) {
        parameterIndex.put(parametersMap.get(i).keySet().iterator().next(), i);
      }
      parametersIndex.put(command, parameterIndex);
    });
  }

}
