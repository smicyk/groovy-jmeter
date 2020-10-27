ruleset {
    // Basic
    AssertWithinFinallyBlock
    AssignmentInConditional
    BigDecimalInstantiation
    BitwiseOperatorInConditional
    BooleanGetBoolean
    BrokenNullCheck
    BrokenOddnessCheck
    ClassForName
    ComparisonOfTwoConstants
    ComparisonWithSelf
    ConstantAssertExpression
    ConstantIfExpression
    ConstantTernaryExpression
    DeadCode
    DoubleNegative
    DuplicateCaseStatement
    DuplicateMapKey
    DuplicateSetValue
    EmptyCatchBlock
    EmptyClass
    EmptyElseBlock
    EmptyFinallyBlock
    EmptyForStatement
    EmptyIfStatement
    EmptyInstanceInitializer
    EmptyMethod
    EmptyStaticInitializer
    EmptySwitchStatement
    EmptySynchronizedStatement
    EmptyTryBlock
    EmptyWhileStatement
    EqualsAndHashCode
    EqualsOverloaded
    ExplicitGarbageCollection
    ForLoopShouldBeWhileLoop
    HardCodedWindowsFileSeparator
    HardCodedWindowsRootDirectory
    IntegerGetInteger
    MultipleUnaryOperators
    RandomDoubleCoercedToZero
    RemoveAllOnSelf
    ReturnFromFinallyBlock
    ThrowExceptionFromFinallyBlock
    
    // Braces
    ElseBlockBraces
    ForStatementBraces
    IfStatementBraces
    WhileStatementBraces

    // Comments
    ClassJavadoc
    JavadocConsecutiveEmptyLines
    JavadocEmptyAuthorTag
    JavadocEmptyExceptionTag
    JavadocEmptyFirstLine
    JavadocEmptyLastLine
    JavadocEmptyParamTag
    JavadocEmptyReturnTag
    JavadocEmptySeeTag
    JavadocEmptySinceTag
    JavadocEmptyThrowsTag
    JavadocEmptyVersionTag
    JavadocMissingExceptionDescription
    JavadocMissingParamDescription
    JavadocMissingThrowsDescription
    
    // Concurency
    BusyWait
    DoubleCheckedLocking
    InconsistentPropertyLocking
    InconsistentPropertySynchronization
    NestedSynchronization
    StaticCalendarField
    StaticConnection
    StaticDateFormatField
    StaticMatcherField
    StaticSimpleDateFormatField
    SynchronizedMethod
    SynchronizedOnBoxedPrimitive
    SynchronizedOnGetClass
    SynchronizedOnReentrantLock
    SynchronizedOnString
    SynchronizedOnThis
    SynchronizedReadObjectMethod
    SystemRunFinalizersOnExit
    ThisReferenceEscapesConstructor
    ThreadGroup
    ThreadLocalNotStaticFinal
    ThreadYield
    UseOfNotifyMethod
    VolatileArrayField
    VolatileLongOrDoubleField
    WaitOutsideOfWhileLoop
    
    // Convention
    CompileStatic
    ConfusingTernary
    CouldBeElvis
    CouldBeSwitchStatement
    FieldTypeRequired
    HashtableIsObsolete
    IfStatementCouldBeTernary
    InvertedCondition
    InvertedIfElse
    LongLiteralWithLowerCaseL
    MethodParameterTypeRequired
    MethodReturnTypeRequired
    NoDef
    NoJavaUtilDate
    NoTabCharacter
    ParameterReassignment
    PublicMethodsBeforeNonPublicMethods
    StaticFieldsBeforeInstanceFields
    StaticMethodsBeforeInstanceMethods
    TernaryCouldBeElvis
    TrailingComma
    VariableTypeRequired
    VectorIsObsolete
    
    // Design
    AbstractClassWithPublicConstructor
    AbstractClassWithoutAbstractMethod
    AssignmentToStaticFieldFromInstanceMethod
    BooleanMethodReturnsNull
    BuilderMethodWithSideEffects
    CloneableWithoutClone
    CloseWithoutCloseable
    CompareToWithoutComparable
    ConstantsOnlyInterface
    EmptyMethodInAbstractClass
    FinalClassWithProtectedMember
    ImplementationAsType
    Instanceof
    LocaleSetDefault
    NestedForLoop
    PrivateFieldCouldBeFinal
    PublicInstanceField
    ReturnsNullInsteadOfEmptyArray
    ReturnsNullInsteadOfEmptyCollection
    SimpleDateFormatMissingLocale
    StatelessSingleton
    ToStringReturnsNull
    
    // Dry
    DuplicateListLiteral
    DuplicateMapLiteral
    DuplicateNumberLiteral
    DuplicateStringLiteral
    
    // Enhanced // disable by default since gradle plugin doens't support it
    //CloneWithoutCloneable
    //JUnitAssertEqualsConstantActualValue
    //MissingOverrideAnnotation
    //UnsafeImplementationAsMap
    
    // Exception
    CatchArrayIndexOutOfBoundsException
    CatchError
    CatchException
    CatchIllegalMonitorStateException
    CatchIndexOutOfBoundsException
    CatchNullPointerException
    CatchRuntimeException
    CatchThrowable
    ConfusingClassNamedException
    ExceptionExtendsError
    ExceptionExtendsThrowable
    ExceptionNotThrown
    MissingNewInThrowStatement
    ReturnNullFromCatchBlock
    SwallowThreadDeath
    ThrowError
    ThrowException
    ThrowNullPointerException
    ThrowRuntimeException
    ThrowThrowable
    
    // Formatting
    BlankLineBeforePackage
    BlockEndsWithBlankLine
    BlockStartsWithBlankLine
    BracesForClass
    BracesForForLoop
    BracesForIfElse
    BracesForMethod
    BracesForTryCatchFinally
    //ClassEndsWithBlankLine
    ClassStartsWithBlankLine
    ClosureStatementOnOpeningLineOfMultipleLineClosure
    ConsecutiveBlankLines
    FileEndsWithoutNewline
    Indentation
    LineLength
    MissingBlankLineAfterImports
    MissingBlankLineAfterPackage
    SpaceAfterCatch
    SpaceAfterClosingBrace
    SpaceAfterComma
    SpaceAfterFor
    SpaceAfterIf
    SpaceAfterOpeningBrace
    SpaceAfterSemicolon
    SpaceAfterSwitch
    SpaceAfterWhile
    SpaceAroundClosureArrow
    SpaceAroundMapEntryColon {
        characterAfterColonRegex = /\s/
    }
    SpaceAroundOperator
    SpaceBeforeClosingBrace
    SpaceBeforeOpeningBrace
    TrailingWhitespace
    
    // Generic
    IllegalClassMember
    IllegalClassReference
    IllegalPackageReference
    IllegalRegex
    IllegalString
    IllegalSubclass
    RequiredRegex
    RequiredString
    StatelessClass
    
    // Groovyism
    AssignCollectionSort
    AssignCollectionUnique
    ClosureAsLastMethodParameter
    CollectAllIsDeprecated
    ConfusingMultipleReturns
    ExplicitArrayListInstantiation
    ExplicitCallToAndMethod
    ExplicitCallToCompareToMethod
    ExplicitCallToDivMethod
    ExplicitCallToEqualsMethod
    ExplicitCallToGetAtMethod
    ExplicitCallToLeftShiftMethod
    ExplicitCallToMinusMethod
    ExplicitCallToModMethod
    ExplicitCallToMultiplyMethod
    ExplicitCallToOrMethod
    ExplicitCallToPlusMethod
    ExplicitCallToPowerMethod
    ExplicitCallToPutAtMethod
    ExplicitCallToRightShiftMethod
    ExplicitCallToXorMethod
    ExplicitHashMapInstantiation
    ExplicitHashSetInstantiation
    ExplicitLinkedHashMapInstantiation
    ExplicitLinkedListInstantiation
    ExplicitStackInstantiation
    ExplicitTreeSetInstantiation
    GStringAsMapKey
    GStringExpressionWithinString
    GetterMethodCouldBeProperty
    GroovyLangImmutable
    UseCollectMany
    UseCollectNested
    
    // Imports
    DuplicateImport
    ImportFromSamePackage
    ImportFromSunPackages
    MisorderedStaticImports {
        comesBefore = false
    }
    NoWildcardImports
    UnnecessaryGroovyImport
    UnusedImport
    
    // JUnit
    ChainedTest
    CoupledTestCase
    JUnitAssertAlwaysFails
    JUnitAssertAlwaysSucceeds
    JUnitFailWithoutMessage
    JUnitLostTest
    JUnitPublicField
    JUnitPublicNonTestMethod
    JUnitPublicProperty
    JUnitSetUpCallsSuper
    JUnitStyleAssertions
    JUnitTearDownCallsSuper
    JUnitTestMethodWithoutAssert
    JUnitUnnecessarySetUp
    JUnitUnnecessaryTearDown
    JUnitUnnecessaryThrowsException
    SpockIgnoreRestUsed
    UnnecessaryFail
    UseAssertEqualsInsteadOfAssertTrue
    UseAssertFalseInsteadOfNegation
    UseAssertNullInsteadOfAssertEquals
    UseAssertSameInsteadOfAssertTrue
    UseAssertTrueInsteadOfAssertEquals
    UseAssertTrueInsteadOfNegation
    
    // Logging
    LoggerForDifferentClass
    LoggerWithWrongModifiers
    LoggingSwallowsStacktrace
    MultipleLoggers
    PrintStackTrace
    Println
    SystemErrPrint
    SystemOutPrint
    
    // Naming
    AbstractClassName
    ClassName
    ClassNameSameAsFilename
    ClassNameSameAsSuperclass
    ConfusingMethodName
    FactoryMethodName
    FieldName
    InterfaceName
    InterfaceNameSameAsSuperInterface
    MethodName
    ObjectOverrideMisspelledMethodName
    PackageName
    PackageNameMatchesFilePath
    ParameterName
    PropertyName
    VariableName
    
    // Security
    FileCreateTempFile
    InsecureRandom
    //JavaIoPackageAccess
    NonFinalPublicField
    NonFinalSubclassOfSensitiveInterface
    ObjectFinalize
    PublicFinalizeMethod
    SystemExit
    UnsafeArrayDeclaration
    
    // Serialization
    EnumCustomSerializationIgnored
    SerialPersistentFields
    SerialVersionUID
    SerializableClassMustDefineSerialVersionUID
    
    
    AbcMetric // (Requires the GMetrics jar)
    ClassSize
    CrapMetric //(Requires the GMetrics jar and a Cobertura coverage file)
    CyclomaticComplexity //(Requires the GMetrics jar)
    MethodCount
    MethodSize
    NestedBlockDepth
    ParameterCount
    
    // Unnecessary
    AddEmptyString
    ConsecutiveLiteralAppends
    ConsecutiveStringConcatenation
    UnnecessaryBigDecimalInstantiation
    UnnecessaryBigIntegerInstantiation
    UnnecessaryBooleanExpression
    UnnecessaryBooleanInstantiation
    UnnecessaryCallForLastElement
    UnnecessaryCallToSubstring
    UnnecessaryCast
    UnnecessaryCatchBlock
    UnnecessaryCollectCall
    UnnecessaryCollectionCall
    UnnecessaryConstructor
    UnnecessaryDefInFieldDeclaration
    UnnecessaryDefInMethodDeclaration
    UnnecessaryDefInVariableDeclaration
    UnnecessaryDotClass
    UnnecessaryDoubleInstantiation
    UnnecessaryElseStatement
    UnnecessaryFinalOnPrivateMethod
    UnnecessaryFloatInstantiation
    UnnecessaryGString
    UnnecessaryGetter
    UnnecessaryIfStatement
    UnnecessaryInstanceOfCheck
    UnnecessaryInstantiationToGetClass
    UnnecessaryIntegerInstantiation
    UnnecessaryLongInstantiation
    UnnecessaryModOne
    UnnecessaryNullCheck
    UnnecessaryNullCheckBeforeInstanceOf
    UnnecessaryObjectReferences
    UnnecessaryOverridingMethod
    UnnecessaryPackageReference
    UnnecessaryParenthesesForMethodCallWithClosure
    UnnecessaryPublicModifier
    // UnnecessaryReturnKeyword
    UnnecessarySafeNavigationOperator
    UnnecessarySelfAssignment
    UnnecessarySemicolon
    UnnecessarySetter
    UnnecessaryStringInstantiation
    UnnecessarySubstring
    UnnecessaryTernaryExpression
    UnnecessaryToString
    UnnecessaryTransientModifier
    
    // Unused
    UnusedArray
    UnusedMethodParameter
    UnusedObject
    UnusedPrivateField
    UnusedPrivateMethod
    UnusedPrivateMethodParameter
    UnusedVariable
}