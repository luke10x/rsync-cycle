package dev.luke10x.rsynccycle.provision;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class ProvisionServiceTest {

    static class ExecuteTestArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(
            ExtensionContext extensionContext
        ) {
            return Stream.of(
                Arguments.of(
                    "rsync -avz source/ destination/",
                    new String[] {"rsync", "-avz", "source/", "destination/"}
                ),
                Arguments.of(
                    "rsync -av  -e 'docker exec -i' cont:dir1/dir2 dir3/dir4",
                    new String[] {
                        "rsync",
                        "-av",
                        "-e",
                        "docker exec -i",
                        "cont:dir1/dir2",
                        "dir3/dir4"
                    }
                ),
                Arguments.of(
                    "bash -c \"DOCKER_HOST=dockerhost:2375 rsync -avz -e 'docker exec -i' cont:/src /dst\"",
                    new String[]{
                        "bash",
                        "-c",
                        "DOCKER_HOST=dockerhost:2375 rsync -avz -e 'docker exec -i' cont:/src /dst"
                    }
                )
            );
        }
    }

    @Mock
    private ProcessExecutor processExecutor;

    @ParameterizedTest(name = "{index}: with {arguments}")
    @ArgumentsSource(ExecuteTestArgumentsProvider.class)
    public void testExecute(String command, String[] expectedArgs) {
        ProvisionService provisionService = new ProvisionService(processExecutor);

        provisionService.execute(command);

        verify(processExecutor).execute(
            argThat(args -> {
                assertThat(args).containsExactly(expectedArgs);
                return true;
            }));
    }
}
