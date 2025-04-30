package industries.werwolf.training.layers.presenter;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@SuppressWarnings("unused")
@AnalyzeClasses(packages = ArchitectureTest.BASE_PACKAGE)
class ArchitectureTest {

    static final String BASE_PACKAGE = "industries.werwolf.training.layers.presenter";

    // TODO Add your own rules and remove those that don't apply to your project

    @ArchTest
    public static final ArchRule there_should_not_be_circular_dependencies_between_feature_packages = slices()
            .matching(BASE_PACKAGE + ".(*)..").should().beFreeOfCycles();
}
