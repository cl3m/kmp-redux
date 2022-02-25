import dev.icerock.moko.kswift.plugin.context.ClassContext
import dev.icerock.moko.kswift.plugin.feature.ProcessorContext
import dev.icerock.moko.kswift.plugin.feature.ProcessorFeature
import dev.icerock.moko.kswift.plugin.feature.BaseConfig
import dev.icerock.moko.kswift.plugin.feature.Filter
import io.outfoxx.swiftpoet.*
import java.util.*
import kotlin.reflect.KClass


class EnumGenerator(
    override val featureContext: KClass<ClassContext>,
    override val filter: Filter<ClassContext>
) : ProcessorFeature<ClassContext>() {
    override fun doProcess(featureContext: ClassContext, processorContext: ProcessorContext) {
        if (featureContext.clazz.enumEntries.isEmpty()) return
        val fileSpec: FileSpec.Builder = processorContext.fileSpecBuilder
        val frameworkName: String = processorContext.framework.baseName
        val enumEntries = featureContext.clazz.enumEntries.map {
            it.toLowerCase().split('_').joinToString("", transform = String::capitalize).decapitalize(Locale.ROOT)
        }
        val classSimpleName = featureContext.clazz.name.substringAfterLast('/')

        val enumType: TypeSpec = TypeSpec.enumBuilder("Enum")
            .apply {
                enumEntries.forEach { addEnumCase(it) }
            }
            .build()

        fileSpec.addExtension(
            ExtensionSpec
                .builder(
                    DeclaredTypeName.typeName("$frameworkName.$classSimpleName")
                )
                .addModifiers(Modifier.PUBLIC)
                .addType(enumType)
                .addProperty(
                    PropertySpec.builder("enum", DeclaredTypeName.typeName("$frameworkName.$classSimpleName.Enum"))
                        .getter(FunctionSpec.getterBuilder().addCode(
                            CodeBlock.builder().apply {
                                enumEntries.forEachIndexed { index, enumCase ->
                                    buildString {
                                        if (index != 0) append("} else ")
                                        append("if self == $frameworkName.$classSimpleName.$enumCase {\n")
                                    }.also { add(it) }
                                    indent()
                                    add("return .$enumCase\n")
                                    unindent()
                                }
                                add("} else {\n")
                                indent()
                                add("fatalError(\"$classSimpleName not synchronized\")\n")
                                unindent()
                                add("}\n")
                            }
                            .build()
                        ).build())
                    .build()
                ).build()
        )
    }

    class Config : BaseConfig<ClassContext> {
        override var filter: Filter<ClassContext> = Filter.Exclude(emptySet())
    }

    companion object : Factory<ClassContext, EnumGenerator, Config> {
        override fun create(block: Config.() -> Unit): EnumGenerator {
            val config = Config().apply(block)
            return EnumGenerator(featureContext, config.filter)
        }

        override val featureContext: KClass<ClassContext> = ClassContext::class

        @JvmStatic
        override val factory = Companion
    }
}