package io.github.sashirestela.openai.domain.response;

import io.github.sashirestela.cleverclient.util.JsonUtil;
import io.github.sashirestela.openai.common.function.FunctionDef;
import io.github.sashirestela.openai.domain.assistant.RankingOption;
import io.github.sashirestela.openai.domain.response.Action.ClickAction;
import io.github.sashirestela.openai.domain.response.Action.Coord;
import io.github.sashirestela.openai.domain.response.Action.DoubleClickAction;
import io.github.sashirestela.openai.domain.response.Action.DragAction;
import io.github.sashirestela.openai.domain.response.Action.KeyPressAction;
import io.github.sashirestela.openai.domain.response.Action.MouseButton;
import io.github.sashirestela.openai.domain.response.Action.MoveAction;
import io.github.sashirestela.openai.domain.response.Action.ScreenshotAction;
import io.github.sashirestela.openai.domain.response.Action.ScrollAction;
import io.github.sashirestela.openai.domain.response.Action.TypeAction;
import io.github.sashirestela.openai.domain.response.Action.WaitAction;
import io.github.sashirestela.openai.domain.response.Input.BasicLogProb;
import io.github.sashirestela.openai.domain.response.Input.Citation.ContainerFileCitation;
import io.github.sashirestela.openai.domain.response.Input.Citation.FileCitation;
import io.github.sashirestela.openai.domain.response.Input.Citation.FilePath;
import io.github.sashirestela.openai.domain.response.Input.Citation.UrlCitation;
import io.github.sashirestela.openai.domain.response.Input.CodeInterpreterFile;
import io.github.sashirestela.openai.domain.response.Input.CodeInterpreterOutput.FileOutput;
import io.github.sashirestela.openai.domain.response.Input.CodeInterpreterOutput.TextOutput;
import io.github.sashirestela.openai.domain.response.Input.Content.FileInputContent;
import io.github.sashirestela.openai.domain.response.Input.Content.ImageInputContent;
import io.github.sashirestela.openai.domain.response.Input.Content.TextInputContent;
import io.github.sashirestela.openai.domain.response.Input.FileSearchResult;
import io.github.sashirestela.openai.domain.response.Input.InputMessage;
import io.github.sashirestela.openai.domain.response.Input.Item.CodeInterpreterCallItem;
import io.github.sashirestela.openai.domain.response.Input.Item.ComputerCallItem;
import io.github.sashirestela.openai.domain.response.Input.Item.ComputerCallOutputItem;
import io.github.sashirestela.openai.domain.response.Input.Item.FileSearchCallItem;
import io.github.sashirestela.openai.domain.response.Input.Item.FunctionCallItem;
import io.github.sashirestela.openai.domain.response.Input.Item.FunctionCallOutputItem;
import io.github.sashirestela.openai.domain.response.Input.Item.ImageGenerationCallItem;
import io.github.sashirestela.openai.domain.response.Input.Item.InputMessageItem;
import io.github.sashirestela.openai.domain.response.Input.Item.LocalShellCallItem;
import io.github.sashirestela.openai.domain.response.Input.Item.LocalShellCallOutputItem;
import io.github.sashirestela.openai.domain.response.Input.Item.McpApprovalRequestItem;
import io.github.sashirestela.openai.domain.response.Input.Item.McpApprovalResponseItem;
import io.github.sashirestela.openai.domain.response.Input.Item.McpCallItem;
import io.github.sashirestela.openai.domain.response.Input.Item.McpListToolsItem;
import io.github.sashirestela.openai.domain.response.Input.Item.OutputMessageItem;
import io.github.sashirestela.openai.domain.response.Input.Item.ReasoningItem;
import io.github.sashirestela.openai.domain.response.Input.Item.WebSearchCallItem;
import io.github.sashirestela.openai.domain.response.Input.ItemReference;
import io.github.sashirestela.openai.domain.response.Input.ItemStatus;
import io.github.sashirestela.openai.domain.response.Input.LogProb;
import io.github.sashirestela.openai.domain.response.Input.McpTool;
import io.github.sashirestela.openai.domain.response.Input.MessageRole;
import io.github.sashirestela.openai.domain.response.Input.OutputContent.RefusalOutputContent;
import io.github.sashirestela.openai.domain.response.Input.OutputContent.TextOutputContent;
import io.github.sashirestela.openai.domain.response.Input.ReasoningContent;
import io.github.sashirestela.openai.domain.response.Input.SafetyCheck;
import io.github.sashirestela.openai.domain.response.Input.ScreenshotImage;
import io.github.sashirestela.openai.domain.response.Input.SearchStatus;
import io.github.sashirestela.openai.domain.response.Input.ShellAction;
import io.github.sashirestela.openai.domain.response.ResponseTool.CodeInterpreterResponseTool;
import io.github.sashirestela.openai.domain.response.ResponseTool.ComparisonOperator;
import io.github.sashirestela.openai.domain.response.ResponseTool.ComputerResponseTool;
import io.github.sashirestela.openai.domain.response.ResponseTool.ContainerAuto;
import io.github.sashirestela.openai.domain.response.ResponseTool.Environment;
import io.github.sashirestela.openai.domain.response.ResponseTool.FileSearchResponseTool;
import io.github.sashirestela.openai.domain.response.ResponseTool.Filter.ComparisonFilter;
import io.github.sashirestela.openai.domain.response.ResponseTool.Filter.CompoundFilter;
import io.github.sashirestela.openai.domain.response.ResponseTool.FunctionResponseTool;
import io.github.sashirestela.openai.domain.response.ResponseTool.ImageBackground;
import io.github.sashirestela.openai.domain.response.ResponseTool.ImageFormat;
import io.github.sashirestela.openai.domain.response.ResponseTool.ImageQuality;
import io.github.sashirestela.openai.domain.response.ResponseTool.LocalShellResponseTool;
import io.github.sashirestela.openai.domain.response.ResponseTool.LogicalOperator;
import io.github.sashirestela.openai.domain.response.ResponseTool.McpListTools;
import io.github.sashirestela.openai.domain.response.ResponseTool.McpResponseTool;
import io.github.sashirestela.openai.domain.response.ResponseTool.McpToolApprovalFilter;
import io.github.sashirestela.openai.domain.response.ResponseTool.WebSearchResponseTool;
import io.github.sashirestela.openai.domain.response.ResponseToolChoice.FunctionTool;
import io.github.sashirestela.openai.domain.response.ResponseToolChoice.HostedTool;
import io.github.sashirestela.openai.support.JsonSchemaUtil;
import io.github.sashirestela.openai.test.utils.UtilSpecs;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseDomainSerDeserTest {

    @Test
    void testSerDeserInputClasses() {
        var oneInputMessage = InputMessage.of("message", MessageRole.DEVELOPER);
        var newInputMessage = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneInputMessage),
                InputMessage.class);
        assertEquals(oneInputMessage.toString(), newInputMessage.toString());

        var oneItemReference = ItemReference.of("id");
        var newItemReference = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneItemReference),
                ItemReference.class);
        assertEquals(oneItemReference.toString(), newItemReference.toString());
    }

    @Test
    void testSerDeserContentClasses() {
        var oneTextInputContent = TextInputContent.of("text");
        var newTextInputContent = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneTextInputContent),
                TextInputContent.class);
        assertEquals(oneTextInputContent.toString(), newTextInputContent.toString());

        var oneImageInputContent = ImageInputContent.of("imageUrl");
        var newImageInputContent = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneImageInputContent),
                ImageInputContent.class);
        assertEquals(oneImageInputContent.toString(), newImageInputContent.toString());

        var oneFileInputContent = FileInputContent.builder()
                .fileId("fileId")
                .fileData("fileData")
                .filename("filename")
                .build();
        var newFileInputContent = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneFileInputContent),
                FileInputContent.class);
        assertEquals(oneFileInputContent.toString(), newFileInputContent.toString());
    }

    @Test
    void testSerDeserInputItemClasses() {
        var oneInputMessageItem = InputMessageItem.builder()
                .content(List.of(TextInputContent.of("text")))
                .role(MessageRole.DEVELOPER)
                .status(ItemStatus.COMPLETED)
                .build();
        var newInputMessageItem = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneInputMessageItem),
                InputMessageItem.class);
        assertEquals(oneInputMessageItem.toString(), newInputMessageItem.toString());

        var oneOutputMessageItem = OutputMessageItem.builder()
                .content(List.of(TextOutputContent.builder()
                        .annotations(List.of(FilePath.of("fileId", 0)))
                        .text("text")
                        .build()))
                .id("id")
                .status(ItemStatus.IN_PROGRESS)
                .build();
        var newOutputMessageItem = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneOutputMessageItem),
                OutputMessageItem.class);
        assertEquals(oneOutputMessageItem.toString(), newOutputMessageItem.toString());

        var oneFileSearchCallItem = FileSearchCallItem.builder()
                .id("id")
                .queries(List.of("query1", "query2"))
                .status(SearchStatus.COMPLETED)
                .results(List.of(FileSearchResult.builder()
                        .attributes(Map.of("key1", 10, "key2", "value"))
                        .fileId("fileId")
                        .filename("filename")
                        .score(0.8)
                        .text("text")
                        .build()))
                .build();
        var newFileSearchCallItem = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneFileSearchCallItem),
                FileSearchCallItem.class);
        assertEquals(oneFileSearchCallItem.toString(), newFileSearchCallItem.toString());

        var oneComputerCallItem = ComputerCallItem.builder()
                .action(ScreenshotAction.of())
                .callId("calId")
                .id("id")
                .pendingSafetyChecks(List.of(SafetyCheck.builder()
                        .id("id")
                        .code("code")
                        .message("message")
                        .build()))
                .status(ItemStatus.INCOMPLETE)
                .build();
        var newComputerCallItem = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneComputerCallItem),
                ComputerCallItem.class);
        assertEquals(oneComputerCallItem.toString(), newComputerCallItem.toString());

        var oneComputerCallOutputItem = ComputerCallOutputItem.builder()
                .callId("callId")
                .output(ScreenshotImage.builder()
                        .fileId("fileId")
                        .imageUrl("imageUrl")
                        .build())
                .acknowledgedSafetyChecks(List.of(SafetyCheck.builder()
                        .id("id")
                        .code("code")
                        .message("message")
                        .build()))
                .id("id")
                .status(ItemStatus.COMPLETED)
                .build();
        var newComputerCallOutputItem = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneComputerCallOutputItem),
                ComputerCallOutputItem.class);
        assertEquals(oneComputerCallOutputItem.toString(), newComputerCallOutputItem.toString());

        var oneWebSearchCallItem = WebSearchCallItem.builder()
                .id("id")
                .status(SearchStatus.SEARCHING)
                .build();
        var newWebSearchCallItem = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneWebSearchCallItem),
                WebSearchCallItem.class);
        assertEquals(oneWebSearchCallItem.toString(), newWebSearchCallItem.toString());

        var oneFunctionCallItem = FunctionCallItem.builder()
                .arguments("arguments")
                .callId("callId")
                .name("name")
                .id("id")
                .status(ItemStatus.IN_PROGRESS)
                .build();
        var newFunctionCallItem = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneFunctionCallItem),
                FunctionCallItem.class);
        assertEquals(oneFunctionCallItem.toString(), newFunctionCallItem.toString());

        var oneFunctionCallOutputItem = FunctionCallOutputItem.builder()
                .callId("callId")
                .output("output")
                .id("id")
                .status(ItemStatus.COMPLETED)
                .build();
        var newFunctionCallOutputItem = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneFunctionCallOutputItem),
                FunctionCallOutputItem.class);
        assertEquals(oneFunctionCallOutputItem.toString(), newFunctionCallOutputItem.toString());

        var oneReasoningItem = ReasoningItem.builder()
                .id("id")
                .summary(List.of(ReasoningContent.of("text")))
                .encryptedContent("encryptedContent")
                .status(ItemStatus.COMPLETED)
                .build();
        var newReasoningItem = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneReasoningItem),
                ReasoningItem.class);
        assertEquals(oneReasoningItem.toString(), newReasoningItem.toString());

        var oneImageGenerationCallItem = ImageGenerationCallItem.builder()
                .id("id")
                .result("result")
                .status(ItemStatus.INCOMPLETE)
                .revisedPrompt("revisedPrompt")
                .background(ImageBackground.TRANSPARENT)
                .outputFormat(ImageFormat.PNG)
                .quality(ImageQuality.MEDIUM)
                .size("1024x1024")
                .build();
        var newImageGenerationCallItem = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneImageGenerationCallItem),
                ImageGenerationCallItem.class);
        assertEquals(oneImageGenerationCallItem.toString(), newImageGenerationCallItem.toString());

        var oneCodeInterpreterCallItem = CodeInterpreterCallItem.builder()
                .id("id")
                .code("code")
                .outputs(List.of(TextOutput.of("logs")))
                .status(ItemStatus.COMPLETED)
                .containerId("containerId")
                .build();
        var newCodeInterpreterCallItem = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneCodeInterpreterCallItem),
                CodeInterpreterCallItem.class);
        assertEquals(oneCodeInterpreterCallItem.toString(), newCodeInterpreterCallItem.toString());

        var oneLocalShellCallItem = LocalShellCallItem.builder()
                .id("id")
                .action(ShellAction.builder()
                        .command(List.of("cmd1", "cmd2"))
                        .env("env")
                        .timeoutMs(100)
                        .user("user")
                        .workingDirectory("workingDirectory")
                        .build())
                .callId("callId")
                .status(ItemStatus.IN_PROGRESS)
                .build();
        var newLocalShellCallItem = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneLocalShellCallItem),
                LocalShellCallItem.class);
        assertEquals(oneLocalShellCallItem.toString(), newLocalShellCallItem.toString());

        var oneLocalShellCallOutputItem = LocalShellCallOutputItem.builder()
                .id("id")
                .output("output")
                .status(ItemStatus.COMPLETED)
                .build();
        var newLocalShellCallOutputItem = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneLocalShellCallOutputItem),
                LocalShellCallOutputItem.class);
        assertEquals(oneLocalShellCallOutputItem.toString(), newLocalShellCallOutputItem.toString());

        var oneMcpListToolsItem = McpListToolsItem.builder()
                .id("id")
                .serverLabel("serverLabel")
                .tools(List.of(McpTool.builder()
                        .inputSchema(JsonSchemaUtil.classToJsonSchema(UtilSpecs.DemoSchema.class))
                        .name("name")
                        .annotations("annotations")
                        .description("description")
                        .build()))
                .error("error")
                .build();
        var newMcpListToolsItem = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneMcpListToolsItem),
                McpListToolsItem.class);
        assertEquals(oneMcpListToolsItem.toString(), newMcpListToolsItem.toString());

        var oneMcpApprovalRequestItem = McpApprovalRequestItem.builder()
                .id("id")
                .arguments("arguments")
                .name("name")
                .serverLabel("serverLabel")
                .build();
        var newMcpApprovalRequestItem = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneMcpApprovalRequestItem),
                McpApprovalRequestItem.class);
        assertEquals(oneMcpApprovalRequestItem.toString(), newMcpApprovalRequestItem.toString());

        var oneMcpApprovalResponseItem = McpApprovalResponseItem.builder()
                .id("id")
                .approvalRequestId("approvalRequestId")
                .approve(Boolean.TRUE)
                .reason("reason")
                .build();
        var newMcpApprovalResponseItem = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneMcpApprovalResponseItem),
                McpApprovalResponseItem.class);
        assertEquals(oneMcpApprovalResponseItem.toString(), newMcpApprovalResponseItem.toString());

        var oneMcpCallItem = McpCallItem.builder()
                .id("id")
                .arguments("arguments")
                .name("name")
                .serverLabel("serverLabel")
                .error("error")
                .output("output")
                .approvalRequestId("approvalRequestId")
                .build();
        var newMcpCallItem = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneMcpCallItem),
                McpCallItem.class);
        assertEquals(oneMcpCallItem.toString(), newMcpCallItem.toString());
    }

    @Test
    void testSerDeserOutputContentClasses() {
        var oneTextOutputContent = TextOutputContent.builder()
                .annotations(List.of(FilePath.of("fileId", 0)))
                .text("text")
                .logprobs(List.of(LogProb.builder()
                        .bytes(List.of(1))
                        .logprob(10.45)
                        .token("token")
                        .topLogprobs(List.of(BasicLogProb.builder()
                                .bytes(List.of(1))
                                .logprob(10.45)
                                .token("token")
                                .build()))
                        .build()))
                .build();
        var newTextOutputContent = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneTextOutputContent),
                TextOutputContent.class);
        assertEquals(oneTextOutputContent.toString(), newTextOutputContent.toString());

        var oneRefusalOutputContent = RefusalOutputContent.of("refusal");
        var newRefusalOutputContent = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneRefusalOutputContent),
                RefusalOutputContent.class);
        assertEquals(oneRefusalOutputContent.toString(), newRefusalOutputContent.toString());
    }

    @Test
    void testSerDeserCitationClasses() {
        var oneFileCitation = FileCitation.builder()
                .fileId("fileId")
                .index(2)
                .filename("filename")
                .build();
        var newFileCitation = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneFileCitation),
                FileCitation.class);
        assertEquals(oneFileCitation.toString(), newFileCitation.toString());

        var oneUrlCitation = UrlCitation.builder()
                .endIndex(1000)
                .startIndex(500)
                .title("title")
                .url("url")
                .build();
        var newUrlCitation = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneUrlCitation),
                UrlCitation.class);
        assertEquals(oneUrlCitation.toString(), newUrlCitation.toString());

        var oneContainerFileCitation = ContainerFileCitation.builder()
                .containerId("containerId")
                .endIndex(20)
                .startIndex(0)
                .fileId("fileId")
                .build();
        var newContainerFileCitation = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneContainerFileCitation),
                ContainerFileCitation.class);
        assertEquals(oneContainerFileCitation.toString(), newContainerFileCitation.toString());

        var oneFilePath = FilePath.of("fileId", 3);
        var newFilePath = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneFilePath),
                FilePath.class);
        assertEquals(oneFilePath.toString(), newFilePath.toString());
    }

    @Test
    void testSerDeserCodeInterpreterOutputClasses() {
        var oneTextOutput = TextOutput.of("logs");
        var newTextOutput = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneTextOutput),
                TextOutput.class);
        assertEquals(oneTextOutput.toString(), newTextOutput.toString());

        var oneFileOutput = FileOutput.of(List.of(CodeInterpreterFile.of("fileId", "mimeType")));
        var newFileOutput = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneFileOutput),
                FileOutput.class);
        assertEquals(oneFileOutput.toString(), newFileOutput.toString());
    }

    @Test
    void testSerDeserActionClasses() {
        var oneClickAction = ClickAction.of(MouseButton.LEFT, 100, 200);
        var newClickAction = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneClickAction),
                ClickAction.class);
        assertEquals(oneClickAction.toString(), newClickAction.toString());

        var oneDoubleClickAction = DoubleClickAction.of(100, 200);
        var newDoubleClickAction = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneDoubleClickAction),
                DoubleClickAction.class);
        assertEquals(oneDoubleClickAction.toString(), newDoubleClickAction.toString());

        var oneDragAction = DragAction.of(List.of(Coord.of(0, 100), Coord.of(10, 90)));
        var newDragAction = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneDragAction),
                DragAction.class);
        assertEquals(oneDragAction.toString(), newDragAction.toString());

        var oneKeyPressAction = KeyPressAction.of(List.of("k", "e", "y"));
        var newKeyPressAction = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneKeyPressAction),
                KeyPressAction.class);
        assertEquals(oneKeyPressAction.toString(), newKeyPressAction.toString());

        var oneMoveAction = MoveAction.of(100, 200);
        var newMoveAction = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneMoveAction),
                MoveAction.class);
        assertEquals(oneMoveAction.toString(), newMoveAction.toString());

        var oneScreenshotAction = ScreenshotAction.of();
        var newScreenshotAction = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneScreenshotAction),
                ScreenshotAction.class);
        assertEquals(oneScreenshotAction.toString(), newScreenshotAction.toString());

        var oneScrollAction = ScrollAction.of(1, 1, 2, 2);
        var newScrollAction = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneScrollAction),
                ScrollAction.class);
        assertEquals(oneScrollAction.toString(), newScrollAction.toString());

        var oneTypeAction = TypeAction.of("text");
        var newTypeAction = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneTypeAction),
                TypeAction.class);
        assertEquals(oneTypeAction.toString(), newTypeAction.toString());

        var oneWaitAction = WaitAction.of();
        var newWaitAction = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneWaitAction),
                WaitAction.class);
        assertEquals(oneWaitAction.toString(), newWaitAction.toString());
    }

    @Test
    void testSerDeserResponseToolClasses() {
        var oneFileSearchResponseTool = FileSearchResponseTool.of(List.of("vectoreStoreId"));
        var newFileSearchResponseTool = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneFileSearchResponseTool),
                FileSearchResponseTool.class);
        assertEquals(oneFileSearchResponseTool.toString(), newFileSearchResponseTool.toString());

        oneFileSearchResponseTool = FileSearchResponseTool.builder()
                .vectorStoreIds(List.of("vectoreStoreId"))
                .filters(CompoundFilter.builder()
                        .filters(List.of(
                                ComparisonFilter.builder()
                                        .key("key")
                                        .type(ComparisonOperator.GT)
                                        .value(100)
                                        .build(),
                                ComparisonFilter.builder()
                                        .key("key")
                                        .type(ComparisonOperator.LT)
                                        .value(200)
                                        .build()))
                        .type(LogicalOperator.AND)
                        .build())
                .maxNumResults(10)
                .rankingOptions(RankingOption.builder()
                        .scoreThreshold(0.85)
                        .ranker("auto")
                        .build())
                .build();
        newFileSearchResponseTool = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneFileSearchResponseTool),
                FileSearchResponseTool.class);
        assertEquals(oneFileSearchResponseTool.toString(), newFileSearchResponseTool.toString());

        var oneFunctionResponseTool = FunctionResponseTool
                .function(FunctionDef.of(UtilSpecs.CurrentTemperature.class));
        var newFunctionResponseTool = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneFunctionResponseTool),
                FunctionResponseTool.class);
        assertEquals(oneFunctionResponseTool.toString(), newFunctionResponseTool.toString());

        var oneComputerResponseTool = ComputerResponseTool.builder()
                .displayHeight(100)
                .displayWidth(200)
                .environment(Environment.BROWSER)
                .build();
        var newComputerResponseTool = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneComputerResponseTool),
                ComputerResponseTool.class);
        assertEquals(oneComputerResponseTool.toString(), newComputerResponseTool.toString());

        var oneWebSearchResponseTool = WebSearchResponseTool.of();
        var newWebSearchResponseTool = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneWebSearchResponseTool),
                WebSearchResponseTool.class);
        assertEquals(oneWebSearchResponseTool.toString(), newWebSearchResponseTool.toString());

        var oneMcpResponseTool = McpResponseTool.builder()
                .serverLabel("serverLabel")
                .serverUrl("serverUrl")
                .allowedTools(McpListTools.of(List.of("tool1", "tool2")))
                .headers(Map.of("key1", "val1"))
                .requireApproval(McpToolApprovalFilter.builder()
                        .always(McpListTools.of(List.of("tool1", "tool2")))
                        .never(McpListTools.of(List.of("tool1", "tool2")))
                        .build())
                .build();
        var newMcpResponseTool = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneMcpResponseTool),
                McpResponseTool.class);
        assertEquals(oneMcpResponseTool.toString(), newMcpResponseTool.toString());

        var oneCodeInterpreterResponseTool = CodeInterpreterResponseTool.of(
                ContainerAuto.of(List.of("file1", "file2")));
        var newCodeInterpreterResponseTool = JsonUtil.jsonToObject(
                JsonUtil.objectToJson(oneCodeInterpreterResponseTool),
                CodeInterpreterResponseTool.class);
        assertEquals(oneCodeInterpreterResponseTool.toString(), newCodeInterpreterResponseTool.toString());

        var oneLocalShellResponseTool = LocalShellResponseTool.of();
        var newLocalShellResponseTool = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneLocalShellResponseTool),
                LocalShellResponseTool.class);
        assertEquals(oneLocalShellResponseTool.toString(), newLocalShellResponseTool.toString());
    }

    @Test
    void testSerDeserResponseTextClasses() {
        var oneResponseText = ResponseText.text();
        var newResponseText = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneResponseText),
                ResponseText.class);
        assertEquals(oneResponseText.toString(), newResponseText.toString());

        oneResponseText = ResponseText.jsonObject();
        newResponseText = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneResponseText),
                ResponseText.class);
        assertEquals(oneResponseText.toString(), newResponseText.toString());
    }

    @Test
    void testSerDeserResponseToolChoiceClasses() {
        var oneHostedTool = HostedTool.FILE_SEARCH;
        var newHostedTool = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneHostedTool),
                HostedTool.class);
        assertEquals(oneHostedTool.toString(), newHostedTool.toString());

        oneHostedTool = HostedTool.WEB_SEARCH_PREVIEW;
        newHostedTool = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneHostedTool),
                HostedTool.class);
        assertEquals(oneHostedTool.toString(), newHostedTool.toString());

        oneHostedTool = HostedTool.COMPUTER_USE_PREVIEW;
        newHostedTool = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneHostedTool),
                HostedTool.class);
        assertEquals(oneHostedTool.toString(), newHostedTool.toString());

        var oneFunctionTool = FunctionTool.of("name");
        var newFunctionTool = JsonUtil.jsonToObject(JsonUtil.objectToJson(oneFunctionTool),
                FunctionTool.class);
        assertEquals(oneFunctionTool.toString(), newFunctionTool.toString());
    }

}
